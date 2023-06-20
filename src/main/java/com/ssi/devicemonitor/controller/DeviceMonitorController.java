package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.entity.devices.Device;
import com.ssi.devicemonitor.entity.DeviceMonitor;
import com.ssi.devicemonitor.entity.GeneralDevice;
import com.ssi.devicemonitor.entity.devices.HardwareDevice;
import com.ssi.devicemonitor.entity.devices.SoftwareDevice;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.TimerTask;

public class DeviceMonitorController {
    @FXML
    private ListView<Device> deviceListView;

    @FXML
    private TextField deviceNameTextField;

    @FXML
    private Button addDeviceButton;

    @FXML
    private ComboBox<String> deviceTypes;

    private DeviceMonitor deviceMonitor;

    private Color colorErr = Color.rgb(0, 128, 0);
    private Color colorOk = Color.rgb(128, 128, 0);



    //Device default
    @FXML
    private Label manufacturer;
    @FXML
    private Label deviceType;
    @FXML
    private Label version;

    //Hardware
    @FXML
    private Label macAddress;
    @FXML
    private Label loc;
    @FXML
    private Label locLabel;
    @FXML
    private Label macAddressLabel;


    //Software
    @FXML
    private Label installationDate;
    @FXML
    private Label time;
    @FXML
    private Label timeLabel;
    @FXML
    private Label installationDateLabel;



    public void initialize() {
        deviceMonitor = new DeviceMonitor();

        deviceMonitor.addDevice(new HardwareDevice("Device 1", "ManufaX1", "Hardware", "v1", "/dev/d", "000.000.1.000"));
        deviceMonitor.addDevice(new SoftwareDevice("Device 2", "ManufaD1", "Software", "v1", "21.11.1989", "22:22:00:XYZ"));
        deviceMonitor.addDevice(new HardwareDevice("Device 3", "ManufaZ1", "Hardware", "v1", "/dev/d", "000.000.1.000"));

        deviceTypes.getItems().setAll("Hardware", "Software");
        deviceTypes.setValue(deviceTypes.getItems().iterator().next());

        installationDate.setVisible(false);
        time.setVisible(false);
        timeLabel.setVisible(false);
        installationDateLabel.setVisible(false);

        deviceListView.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            System.out.println(selectedDevice);

            //on change software
            if(selectedDevice != null && selectedDevice instanceof SoftwareDevice) {
                var deviceTypeText = selectedDevice.getDeviceType();
                var manufacturerText = selectedDevice.getManufacturer();
                var versionText = selectedDevice.getVersion();
                var timeText = ((SoftwareDevice)selectedDevice).getTime();
                var installationDateText = ((SoftwareDevice)selectedDevice).getInstallationDate();

                deviceType.setText(deviceTypeText);
                manufacturer.setText(manufacturerText);
                version.setText(versionText);
                time.setText(timeText);
                installationDate.setText(installationDateText);

                macAddress.setVisible(false);
                loc.setVisible(false);
                locLabel.setVisible(false);
                macAddressLabel.setVisible(false);
                installationDate.setVisible(true);
                time.setVisible(true);
                timeLabel.setVisible(true);
                installationDateLabel.setVisible(true);
            }
            //on change hardware
            else if(selectedDevice != null && selectedDevice instanceof HardwareDevice){
                var deviceTypeText = selectedDevice.getDeviceType();
                var manufacturerText = selectedDevice.getManufacturer();
                var versionText = selectedDevice.getVersion();
                var locText = ((HardwareDevice)selectedDevice).getLoc();
                var macAddressText = ((HardwareDevice)selectedDevice).getMacAddress();

                deviceType.setText(deviceTypeText);
                manufacturer.setText(manufacturerText);
                version.setText(versionText);
                loc.setText(locText);
                macAddress.setText(macAddressText);


                installationDate.setVisible(false);
                time.setVisible(false);
                timeLabel.setVisible(false);
                installationDateLabel.setVisible(false);
                macAddress.setVisible(true);
                loc.setVisible(true);
                locLabel.setVisible(true);
                macAddressLabel.setVisible(true);

            }
        });

        deviceTypes.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue.equals("Software"));
        });

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceListView.setCellFactory(deviceListView -> new DeviceListCell());

        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");

        removeItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                deviceMonitor.removeDevice(selectedDevice);
                deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
            }
        });

        contextMenu.getItems().addAll(removeItem);
        deviceListView.setContextMenu(contextMenu);

    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            refreshListView();
        }
    }

    @FXML
    private void addDevice() {
        String deviceName = deviceNameTextField.getText();
        String manufacturerText = manufacturer.getText();
        String deviceTypeText = deviceType.getText();
        String versionText = version.getText();

        if(deviceTypes.getSelectionModel().getSelectedItem().equals("Software")) {
            String installationDateText = installationDate.getText();
            String timeText = time.getText();
            Device newDevice = new SoftwareDevice(deviceName, manufacturerText, deviceTypeText, versionText, installationDateText, timeText);
            deviceMonitor.addDevice(newDevice);
        }
        else if(deviceTypes.getSelectionModel().getSelectedItem().equals("Hardware")) {
            String macAddressText = macAddress.getText();
            String locText = loc.getText();
            Device newDevice = new HardwareDevice(deviceName, manufacturerText, deviceTypeText, versionText, locText, macAddressText);
            deviceMonitor.addDevice(newDevice);
        }

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceNameTextField.clear();
    }

    public void refreshListView() {
        deviceListView.refresh();
    }

    private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus().equals("Online")) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus().equals("Offline")) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }
}
