# _____ _____ _____ __ __ _____ _____ 
#|     |   __|     |  |  |     |     |
#|  |  |__   |  |  |_   _|  |  |  |  |
#|_____|_____|_____| |_| |_____|_____|
#
# Use Raspberry Pi to get temperature/humidity from DHT11 sensor
#  
import time
import dht11
import RPi.GPIO as GPIO
import datetime

from google.cloud import firestore
import google.cloud.exceptions

#define GPIO 14 as DHT11 data pin
Temp_sensor=14
def main():
  # Main program block
  GPIO.setwarnings(False)
  GPIO.setmode(GPIO.BCM)       # Use BCM GPIO numbers
  # Initialise display
#  lcd_init()
  instance = dht11.DHT11(pin = Temp_sensor)

  while True:
        #get DHT11 sensor value
        result = instance.read()
        temperature = result.temperature
        humidity = result.humidity
        now = datetime.datetime.now()
        
        if temperature > 0:
            # print("There is a $25 charge for luggage that heavy.",temperature,"C")
            #Auth file
            db = firestore.Client.from_service_account_json('key415-341-24db31ba9bc4.json')
            # [START add_example_data]
            data1 = {
                u'sensorname': u'Class Room',
                u'temperature': temperature,
                u'humidity': humidity,
                u'dateandtime': now,
                u'ownerid': 1
                }
            cities_ref = db.collection(u'th-sensordata')
            cities_ref.document(now.isoformat()).set(data1)
        
        print"Temperature = ",temperature,"C"," Humidity = ",humidity,"%"
        time.sleep(3)

if __name__ == '__main__':

  try:
    main()
  except KeyboardInterrupt:
    pass
#  finally:
#    lcd_byte(0x01, LCD_CMD)
