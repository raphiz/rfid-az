#!/usr/bin/env bash
set -eEuo pipefail

echo "Ensure you run ./gradlew build beforehand!"

# udev rule to simplify access to device + user permissions
sudo cp 10-rfid-reader.rules /etc/udev/rules.d/10-rfid-reader.rules
sudo udevadm control --reload

if [[ -f ~/.config/systemd/user/rfid-az.service ]]; then
  rm ~/.config/systemd/user/rfid-az.service
fi
ln -s "$(pwd)/rfid-az.service" ~/.config/systemd/user/rfid-az.service
systemctl --user enable rfid-az.service

echo "reconnect RFID reader to test"
echo "Then run systemctl --user restart rfid-az.service"
