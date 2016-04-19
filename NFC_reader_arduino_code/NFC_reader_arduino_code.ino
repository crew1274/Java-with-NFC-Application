#include <Wire.h>
#include <SPI.h>
#include <Adafruit_PN532.h>
#define PN532_SCK  (7)
#define PN532_MOSI (5)
#define PN532_SS   (4)
#define PN532_MISO (6)
#define PN532_IRQ   (2)
#define PN532_RESET (3) 
#define led (13) 
Adafruit_PN532 nfc(PN532_SCK, PN532_MISO, PN532_MOSI, PN532_SS);
void setup(void) {
  Serial.begin(9600);
  nfc.begin();
  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {
    Serial.print("Didn't find PN53x board");
    while (1); // halt
  }
  Serial.print("Found chip PN5"); Serial.println((versiondata>>24) & 0xFF, HEX); 
  Serial.print("Firmware ver. "); Serial.print((versiondata>>16) & 0xFF, DEC); 
  Serial.print('.'); Serial.println((versiondata>>8) & 0xFF, DEC);
  Serial.println("Hello! Now start NFC reader.");
  nfc.setPassiveActivationRetries(0xFF);
  nfc.SAMConfig();
}

void loop(void) {
  char c;
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;
  uint8_t dataLength;
  digitalWrite(led,LOW);
  Serial.flush();
  Serial.println("Ready to read Tag,plaese put Mifare Tag on the reader.");
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
  if (success) {  
    if (uidLength == 7)
    { 
      uint8_t data[32];
      Serial.println("Found Mifare Ultralight Tag (7 byte UID)!");
      digitalWrite(led,HIGH);
      Serial.print("#");Serial.println(uidLength, DEC);
      Serial.print("&");nfc.PrintHex(uid, uidLength); 
      for (uint8_t i = 7; i < 40; i++) 
      {
        success = nfc.ntag2xx_ReadPage(i, data);
        if (success) 
        {nfc.PrintHexChar(data, 4);}
      }      
  //Serial.println("Want to update Mifare Ultralight Tag data?");
  Serial.flush();
  while (!Serial.available());
  while (Serial.available()) 
  c=Serial.read();
  if(c!='w')
  {Serial.flush();
   Serial.println("Take out Mifare Ultralight Tag and wait a second!");
   digitalWrite(led,LOW);
   delay(3000);
   return;}
  Serial.flush();
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
  if (success) 
  {
      uint8_t data[32];
      memset(data, 0, 4);
      success = nfc.ntag2xx_ReadPage(3, data);
      if (!success)
      {
        Serial.println("Unable to read the Capability Container (page 3)");
        return;
      }
      else
      {
        if (!((data[0] == 0xE1) && (data[1] == 0x11)))
        {
          Serial.println("This doesn't seem to be an NDEF formatted tag.");
          Serial.println("Page 3 should start with 0xE1 0x10.");
        }
        else
        {
          dataLength = data[2]*8;
          Serial.flush();
          Serial.println("update data!");
          while (!Serial.available());
          while (Serial.available())
    {
          String str;
          str = Serial.readStringUntil('\n');
          char * url=" ";
          strcpy(url,str.c_str());
          //str.toCharArray(url,str.length() + 1);
          Serial.print("Erasing previous data.");
          for (uint8_t i = 6; i < 34; i++) 
          {
            memset(data, 0, 4);
            success = nfc.ntag2xx_WritePage(i, data);
            Serial.print(".");
            if (!success)
            {
              Serial.println(" ERROR!");
              delay(1000);
              return;
            }
          }
          Serial.println(" DONE!");         
          Serial.print("Writing:");
          Serial.print(url);
          Serial.print(" ...");
          uint8_t ndefprefix = NDEF_URIPREFIX_NONE;
          success = nfc.ntag2xx_WriteNDEFURI(ndefprefix, url, dataLength);
          if (success)
          {
            Serial.println("DONE!");
            Serial.println("Take out Mifare Ultralight Tag and wait a second!");
            digitalWrite(led,LOW);
            delay(3000); 
          }
          else
          {
            Serial.println("ERROR!");
            digitalWrite(led,LOW);
            delay(1000);
            return;
     }}}}} 
    }//end if uid=7
    else if (uidLength == 4)
    {
    uint8_t currentblock;
    bool authenticated = false; 
    uint8_t data[16];
    uint8_t keyuniversal[6] = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };
    Serial.println("Found Mifare Classic Tag (4 byte UID)!");
    digitalWrite(led,HIGH);
    Serial.print("#");Serial.println(uidLength, DEC);
    Serial.print("&");nfc.PrintHex(uid, uidLength);
    for (currentblock = 5; currentblock < 64; currentblock++)
    {if(nfc.mifareclassic_IsFirstBlock(currentblock)) 
    {authenticated = false;}
    if (!authenticated)
    {success = nfc.mifareclassic_AuthenticateBlock (uid, uidLength, currentblock, 1, keyuniversal);
      if (success)
      {authenticated = true;}
      else
      {Serial.println("Authentication error");
      delay(3000);
      digitalWrite(led,LOW);
      return;}
    }
     if (!authenticated)
        {
          Serial.print("Block ");Serial.print(currentblock, DEC);Serial.println(" unable to authenticate");
          delay(3000);
          digitalWrite(led,LOW);
          return; }
    else
    { success = nfc.mifareclassic_ReadDataBlock(currentblock, data);
      if (success)
     {nfc.PrintHexChar(data, 16);}
    else
    {Serial.print("Block ");Serial.print(currentblock, DEC);Serial.println(" unable to read this block");
     } } }
  Serial.println("");
  //Serial.println("Want to update Mifare Classic Tag data?");
  Serial.flush();
  while (!Serial.available());
  while (Serial.available()) 
  c=Serial.read();
  if(c!='w')
  {Serial.flush();
   Serial.println("");
   Serial.println("Take out Mifare Classic Tag and wait a second!");
   digitalWrite(led,LOW);
   delay(3000);
   return;}
  Serial.flush();
  uint8_t ndefprefix = NDEF_URIPREFIX_NONE;
  uint8_t keya[6] = { 0xA0, 0xA1, 0xA2, 0xA3, 0xA4, 0xA5 };
  uint8_t keyb[6] = { 0xD3, 0xF7, 0xD3, 0xF7, 0xD3, 0xF7 };
  Serial.println("update data");
  while (!Serial.available());
  while (Serial.available())
    { String str;
      str = Serial.readStringUntil('\n');
      str="@@@@@@@"+str;
      //char * url="";
      //strcpy(url,str.c_str());
      //str.toCharArray(url,str.length() + 1);
  success = nfc.mifareclassic_AuthenticateBlock (uid, uidLength, 4, 0, keyb);
   if (!success)
    {Serial.println("Unable to authenticate block 4 ... is this card NDEF formatted?");
    digitalWrite(led,LOW);
    delay(3000);
    return;}
    Serial.println("Authentication succeeded!");
   for(int i=0;i<=str.length()/38;i++)
   {char *url=" ";
    String strsub=str.substring(i*38,37+i*38);
    strcpy(url,strsub.c_str());
    /*for(int j=1;j<=38;j++)
     {*url_1=*url_1+url[j+(i-1)*38]; }*/
     success = nfc.mifareclassic_WriteNDEFURI(2,ndefprefix,url);
     if (!success)
    {Serial.println("NDEF Record creation failed! :(");
     digitalWrite(led,LOW);
     break;return;
     delay(3000);}}
    Serial.println("DONE!");
    Serial.println("Take out Mifare Classic Tag and wait a second!");
    digitalWrite(led,LOW);
    delay(3000);
    }}
    else
    {Serial.println("This doesn't Mifare Ultralight or Classic Tag");
    delay(1000);
    return;
      }
  }
  }
