package com.sersoluciones.flutter_pos_printer_platform.usb

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.sersoluciones.flutter_pos_printer_platform.adapter.USBPrinterAdapter

class UsbReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("UsbReceiver", "Inside USB Broadcast action ${intent!!.action}")

        val action = intent.action
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED == action) {

            val usbDevice: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

            val mPermissionIndent = if (android.os.Build.VERSION.SDK_INT >= 34) {
                PendingIntent.getBroadcast(context, 0, Intent("com.flutter_pos_printer.USB_PERMISSION"), PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_IMMUTABLE)
            }else {
                PendingIntent.getBroadcast(context, 0, Intent("com.flutter_pos_printer.USB_PERMISSION"), PendingIntent.FLAG_MUTABLE)
            }
            val mUSBManager = context?.getSystemService(Context.USB_SERVICE) as UsbManager?
            mUSBManager?.requestPermission(usbDevice, mPermissionIndent)

        }
    }
}