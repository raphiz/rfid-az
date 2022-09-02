# RFID AZ

This is a simple tool to log work time using a keyboard rfid reader such as the Neuftech USB RFID Reader ID.

## Linux

### Hardware Vendor ID und Product ID auslesen

Für udev rule (siehe `10-rfid-reader.rules`) wird die vendor ID und product id benötigt. Diese können wie folgt
ausgelesen werden:

```bash
$ lsusb | grep RFID
Bus 003 Device 015: ID ffff:0035 Sycreader RFID Technology Co., Ltd SYC ID&IC USB Reader

# Anhand der Bus ID und Device ID die parameter für udevadm anpassen:
udevadm info -a /dev/bus/usb/003/015
...
ATTR{manufacturer}=="Sycreader RFID Technology Co., Ltd"
ATTR{idProduct}=="0035"
...
```

Alternativ kann auch `cat /proc/bus/input/devices` genutzt werden.
