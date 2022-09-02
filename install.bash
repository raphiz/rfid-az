#!/usr/bin/env bash
set -eEuo pipefail

read -r -p "Ensure you run ./gradlew build beforehand. [proceed] " _

echo "Installing udev rule to simplify access to device and to ensure user permissions"
sudo cp 10-rfid-reader.rules /etc/udev/rules.d/10-rfid-reader.rules
sudo udevadm control --reload

read -r -p "(Re-)connect RFID-Reader [proceed] " _

echo "Installing user systemd service"
if [[ -f ~/.config/systemd/user/rfid-az.service ]]; then
  rm ~/.config/systemd/user/rfid-az.service
fi
ln -s "$(pwd)/rfid-az.service" ~/.config/systemd/user/rfid-az.service
systemctl --user daemon-reload
systemctl --user enable rfid-az.service
systemctl --user restart rfid-az.service
systemctl --user status rfid-az.service

echo "Done"
