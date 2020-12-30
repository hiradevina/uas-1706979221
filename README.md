# Youngsil
Fakhira Devina - 1706979221 - Tugas Akhir Semester TKTPL B
[REPO](https://github.com/hiradevina/uas-1706979221)

## Stack Android Framework Standard
---
### Activity
- Activity untuk menambahkan Todo
- Activity pada beranda yang berisi informasi cuaca (WeatherFragment) dan List Todo (DisplayTodoFragment)

### Service & Remote Method
- Menggunakan Alarm Manager untuk Trigger Notifikasi setiap pagi
- Menggunakan Location Manager untuk mengambil lokasi pengguna untuk mendapatkan data cuaca
- Mengambil data Weather dari API Weather

### Content Provider
- Menyimpan deadline Todo pada calendar local device

### Broadcast Receiver
- Menampilkan notifikasi setelah Alarm Manager selesai

### Async Task
- Mengambil data deadline hari ini setiap pagi

## Multi Environment
---
### Multi Layout
- Membuat dua layout berbeda untuk orientation horizontal (+ DetailTodoFragment) dan vertical pada Beranda

### Multi Language
- Menggunaka string resource Bahasa Indonesia dan Bahasa Inggris

## MVVM
---
- Semua activity dan fragment pada projek ini menggunakan ViewModel untuk business logic
- Mengunakan Dependency Injection (Hilt)

## Assets
---
Menggunakan string resource pada strings.xml serta membuat custom icon launcher

## Data Persistance
---
Semua data di projek ini disimpan di Room Database yang memiliki tiga entity, yaitu Todo, Weather, dan Quote

## Runtime Permission
---
Request permission untuk akses lokasi (untuk mengambil data cuaca) dan calendar pengguna (saat menambahkan Todo)

## JNI
---
Mengunakan fungsi native C untuk OpenGL pada splash screen

## OpenGL
---
Menampilkan animasi OpenGL pada splash screen

## Connectivity Manager
---
Hanya dapat mengambil data cuaca jika menggunakan Wifi

## Service Background
---
Menampilkan Todo list hari ini setiap pagi walaupun aplikasi sedang tidak dibuka

## Notifikasi
---
Memberikan notifikasi Todo hari ini pada setiap pagi