Note:  
Tugas Implisit Intent ada pada MainActivity  
Tugas Background Services ada pada Services  
  
Untuk Tes APP silahkan cari AndroidManifest.xml pada App\manifest\ dan ikuti panduan berikut:  
- Untuk activity yang memiliki intent-filter, ubah android:name nya menjadi ".MainActivity" ataupun ".Services" sesuai yang ingin dilihat
- Untuk activity yang hanya memiliki android:exported = false, ubah android:name nya menjadi ".MainActivity" ataupun ".Services" sesuai yang tidak ingin dilihat
