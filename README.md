# Twitter Veri Analizi ve Kullanıcı İlgi Alanları Eşleştirme


---

## Özet
Bu proje, JSON veri seti (Twitter API simülasyonu) kullanarak kullanıcı verilerini çekmeyi, analiz etmeyi ve benzer ilgi alanlarına sahip kullanıcıları eşleştirmeyi amaçlamaktadır. Kullanıcılar arasındaki ilişkiler graf yapısıyla modellenmiş ve ilgi alanları hash tabloları ve bağlı listeler ile organize edilmiştir. Çeşitli arama algoritmaları kullanılarak benzer kullanıcılar tespit edilip ilişkiler graf üzerinde görselleştirilmiştir.  

---

## Anahtar Kelimeler
- Hash Tablosu  
- Bağlı Liste  
- Graf  
- Arama Algoritmaları  
- JSON Veri Analizi  

---

## Giriş
Sosyal medya platformlarında kullanıcıların paylaştığı içeriklerdeki ilgi alanlarını belirlemek ve benzer ilgi alanlarına sahip kullanıcıları eşleştirmek, kişiselleştirilmiş içerik önerileri ve sosyal ağ analizinde önemlidir.  

Bu proje kapsamında:  
- JSON dosyası ile 50.000 kullanıcı verisi oluşturulmuştur.  
- Java dili kullanılarak veri işleme ve analiz gerçekleştirilmiştir.  
- Hash tabloları ve bağlı listeler kullanılarak verilerin hızlı erişimi sağlanmıştır.  
- Kullanıcı ilişkileri graf ile gösterilmiş ve analiz sonuçları `.txt` dosyasına kaydedilmiştir.  

---

## Yöntem
### 1. Veri Toplama
- Kullanıcı verileri JSON formatında oluşturulmuş veya çekilmiştir.  
- Her JSON nesnesi, takipçi, takip edilen ve tweet bilgilerini içerir.  

### 2. Veri İşleme
- JSON verileri parse edilerek kullanıcı bilgileri ayrıştırılmıştır.  
- Kullanıcı nesneleri oluşturulup listeye eklenmiştir.  

### 3. Veri Yapıları
- **Hash Tablosu:** Kullanıcı bilgilerini ve kelime sıklıklarını saklamak için.  
- **Bağlı Liste:** Her kullanıcı için ilgi alanlarını depolamak için.  

### 4. Graf Oluşturma
- Kullanıcılar arasındaki ilişkiler graf ile modellenmiştir.  
- Graf yapısı `.txt` dosyasına yazdırılmıştır.  

### 5. İlgi Alanı Algoritmaları
- Kelime sıklığı hesaplama  
- En çok geçen kelimeleri belirleme  
- Kullanıcı ilgi alanlarını tespit etme  

### 6. Çıktı ve Görselleştirme
- Kullanıcılar ve ilişkileri graf ile görselleştirilmiştir.  
- Ortak ilgi alanına sahip kullanıcı toplulukları tespit edilmiştir.  

---

## Deneysel Sonuçlar
- Kullanıcıların ilgi alanları başarılı bir şekilde belirlenmiştir.  
- Graf yapısı, kullanıcılar arasındaki ilişkileri doğru şekilde göstermiştir.  
- Topluluk analizi ile benzer ilgi alanına sahip kullanıcı grupları tespit edilmiştir.  
- Hash tabloları ve bağlı listeler veri erişimini hızlandırmış, bellek kullanımını optimize etmiştir.  

---

## Sonuç
Bu proje JSON verilerini kullanarak:  
- Kullanıcı bilgilerini çekip yazdırmayı,  
- Kullanıcı ilişkilerini graf ile göstermeyi,  
- İlgi alanlarını tespit edip benzer kullanıcıları eşleştirmeyi başarmıştır.  

Sosyal ağ analizi ve kişiselleştirilmiş içerik önerileri için uygulanabilir bir çözüm sunmaktadır.  

---

## Yazar Katkıları
- Tüm proje tek bir kişi tarafından, **Zeynep Palabıyık**, geliştirilmiştir.  

---

## Kaynaklar
1. [Algoritma ve Akış Şemaları](https://www.uzaktanegitim.com/blog/algoritma-nedir-akis-semasi-nedir)  
2. [PDF Kaynak](http://www.fatihmarasli.com/wp-content/uploads/2018/02/tüm.pdf)  
3. [Hash Table Rehberi](https://cokyamanmuhammet.medium.com/hash-table-karma-tablolar-f774a56b7342)  
4. [YouTube Eğitimleri](https://www.youtube.com/)  
5. [Python JSON Veri İşleme](https://medium.com/@ibrahimirdem/pythonda-json-veri-okuma-ve-oluşturma-ade3e33f6184)  
6. [YouTube JSON Parsing](https://www.youtube.com/watch?v=paMcKZlcv78&t=2039s)  
7. [Ek YouTube Kaynağı](https://www.youtube.com/watch?v=0fGeIVgyAgY&t=2645s)  



tum_kullanicilar.json : https://drive.google.com/file/d/1PGzDnzyNpTtdgFbDmTIMLe_TCWcddNU2/view?usp=share_link
