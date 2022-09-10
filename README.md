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


## Useful Resources
* ["Bind" USB-keyboard exclusively to specific application](https://serverfault.com/questions/385260/bind-usb-keyboard-exclusively-to-specific-application/976557#976557)
* [How to make Linux ignore a keyboard while keeping it available for my program to read?](https://stackoverflow.com/questions/63478999/how-to-make-linux-ignore-a-keyboard-while-keeping-it-available-for-my-program-to)
* [Exploring /dev/input](https://thehackerdiary.wordpress.com/2017/04/21/exploring-devinput-1/)
* [Accessing Keys From Linux Input Device](https://www.faqcode4u.com/faq/16452/accessing-keys-from-linux-input-device)
* [Linux – How to read input from the hosts keyboard when connected via SSH – Unix Server Solutions](https://super-unix.com/superuser/how-to-read-input-from-the-hosts-keyboard-when-connected-via-ssh/)
* [c - Accessing Keys from Linux Input Device - Stack Overflow](https://stackoverflow.com/questions/20943322/accessing-keys-from-linux-input-device)
* [linux/input-event-codes.h at master · torvalds/linux](https://github.com/torvalds/linux/blob/master/include/uapi/linux/input-event-codes.h)
* [Starting systemd service instance for device from udev](https://blog.fraggod.net/2015/01/12/starting-systemd-service-instance-for-device-from-udev.html)