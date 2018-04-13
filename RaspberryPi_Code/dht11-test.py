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
        print"Temperature = ",result.temperature,"C"," Humidity = ",result.humidity,"%"
        time.sleep(1)

if __name__ == '__main__':

  try:
    main()
  except KeyboardInterrupt:
    pass
#  finally:
#    lcd_byte(0x01, LCD_CMD)
