package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.example.ChainingHashTable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        // JSON dosyasından okuma işlemi
        JSONArray oku = new JSONArray(new String(Files.readAllBytes(Paths.get("/Users/zeynep/Desktop/proje 3/python/tum_kullanicilar.json"))));

        // Hash table oluşturuluyor
        ChainingHashTable hash_tablosu = new ChainingHashTable(oku.length());

        // Kullanıcı listesi
        bagliListee<Kullanici> kullanicilar = new bagliListee<>();

        // Kelime sıklıklarını tutmak için liste
        bagliListee<KelimeSiklik> kelimeSiklikListesi = new bagliListee<>();

        bagliListee<String> ilgiAlanlariniTuttugumListe = new bagliListee<>(); // Main içinde yeni bağlı liste oluşturuldu
        bagliListee<String> yeniIlgiAlanlariListesi = new bagliListee<>();
        // JSON dizisindeki her bir nesne üzerinde işlem yapılıyor
        for (int i = 0; i < oku.length(); i++) {
            JSONObject veri = oku.getJSONObject(i);

            String username = veri.getString("username");
            String name = veri.getString("name");
            int followersCount = veri.getInt("followers.count");
            int followingCount = veri.getInt("following.count");
            String language = veri.getString("language");
            String region = veri.getString("region");

            JSONArray tweetsArray = veri.getJSONArray("tweets");
            bagliListee<String> tweets = new bagliListee<>();

            for (int j = 0; j < tweetsArray.length(); j++) {
                tweets.ekle(tweetsArray.getString(j));
            }

            Kullanici kullanici = new Kullanici(username, tweets);
            kullanicilar.ekle(kullanici);

            frekansa_gore(tweets, kelimeSiklikListesi);

            JSONArray followersArray = veri.getJSONArray("followers");
            bagliListee<String> followerNames = new bagliListee<>();
            for (int j = 0; j < followersArray.length(); j++) {
                followerNames.ekle(followersArray.getString(j));
            }

            JSONArray followingArray = veri.getJSONArray("following");
            bagliListee<String> followingNames = new bagliListee<>();
            for (int j = 0; j < followingArray.length(); j++) {
                followingNames.ekle(followingArray.getString(j));
            }

            ChainingHashTable.User user = new ChainingHashTable.User(username, name, followersCount, followingCount, language, region, tweets, followerNames, followingNames);
            hash_tablosu.put(user);

        }

        for (Kullanici kullanici : kullanicilar) {
            // Her bir kullanıcının ilgi alanlarını bul
            bagliListee<String> kullaniciIlgiAlanlari = findInterestAreas(kullanici.tweets, kelimeSiklikListesi);

            // Her bir ilgi alanını ayrı bir bağlı listede sakla
            ilgiAlanlariniTuttugumListe.ekle(String.valueOf(kullaniciIlgiAlanlari));
        }

        // Oluşturulan bağlı listeyi yazdır
        System.out.println("Ilgi Alanlarini Tuttugum Liste:");
        for (String ilgiAlanlari : ilgiAlanlariniTuttugumListe) {
          //  System.out.println(ilgiAlanlari);
        }






        for (String ilgiAlani : ilgiAlanlariniTuttugumListe) {
            boolean eklenmeyecek = false;

            for (String eklenenIlgiAlani : yeniIlgiAlanlariListesi) {
                if (eklenenIlgiAlani.contains(ilgiAlani)) {
                    eklenmeyecek = true;
                    break;
                }
            }

            if (!eklenmeyecek) {
                yeniIlgiAlanlariListesi.ekle(ilgiAlani);
            }
        }

        System.out.println("Yeni Ilgi Alanlari Liste:");
        for (String ilgiAlani : yeniIlgiAlanlariListesi) {
            System.out.println(ilgiAlani);
        }





//   Her bir ilgi alanı için
        for (String ilgiAlani : yeniIlgiAlanlariListesi) {
            // İlgili ilgi alanına sahip olan kullanıcıları bulun
            bagliListee<ChainingHashTable.User> ilgiAlaniKullanicilari = new bagliListee<>();

            for (Kullanici kullanici : kullanicilar) {
                // Kullanıcının ilgi alanları arasında ilgili ilgi alanı varsa
                if (ilgiAlani.equalsIgnoreCase(String.valueOf(findInterestAreas(kullanici.tweets, kelimeSiklikListesi)))) {

                    // İlgili kullanıcıyı ilgi alanına ait kullanıcılar listesine ekle
                    ilgiAlaniKullanicilari.ekle(hash_tablosu.get(kullanici.username));
                }
            }

            // İlgi alanına ait kullanıcı listesini yazdır
            System.out.println("Ilgi Alani: " + ilgiAlani);
            System.out.println("Ilgi Alanina Sahip Kullanicilar:");

            for (ChainingHashTable.User user : ilgiAlaniKullanicilari) {
                // Sadece kullanıcının adını yazdır
                System.out.print(user.username+" ");
            }

            System.out.println("---------");
        }




        hash_tablosu.ekranayaz();
        hash_tablosu.iliskiler();
        hash_tablosu.Graph_cikti();
        ilgi_alanini_yazdir(kullanicilar, kelimeSiklikListesi);
        hash_tablosu.dosya("/Users/zeynep/Desktop/graph.txt");



        Scanner scanner = new Scanner(System.in);
        System.out.print("Kullanici adini giriniz: ");
        String koy = scanner.nextLine();

        ChainingHashTable.User foundUserKoy = hash_tablosu.get(koy);
        if (foundUserKoy != null) {
            System.out.println("bilgileri :");
            System.out.println(foundUserKoy);
        } else {
            System.out.println("kullanici bulunamadi.");
        }

        System.out.print("grafini gormek sistediginiz kullanici adini girin : ");
        String arananKullaniciAdi = scanner.nextLine();

// Kullanıcıyı bul ve grafını yazdır
        ChainingHashTable.User foundUserGraf = hash_tablosu.get(arananKullaniciAdi);
        if (foundUserGraf != null) {
            // Kullanıcının takipçilerini ve takip ettiklerini yazdır
            System.out.println("takip eden: " + foundUserGraf.getFollowerNames());
            System.out.println("takip ettikleri: " + foundUserGraf.getFollowingNames());

            // Eğer grafı dosyaya kaydetmek istiyorsanız
            hash_tablosu.dosya("/Users/zeynep/Desktop/graph.txt");
        } else {
            System.out.println("kullanici bulunamadi");
        }



    }


    private static void ilgi_alanini_yazdir(bagliListee<Kullanici> kullanicilar, bagliListee<KelimeSiklik> kelimeSiklikListesi) {
        System.out.println("\nkullanicilarin ilgi alanlari:");
        for (Kullanici kullanici : kullanicilar) {
            System.out.println("kullanici: " + kullanici.username);
            bagliListee<String> ilgiAlanlari = findInterestAreas(kullanici.tweets, kelimeSiklikListesi);
            if (ilgiAlanlari.isEmpty()) {
                System.out.println("ilgi alani yok");
            } else {
                System.out.println("ilgi alanlari : " + ilgiAlanlari);
            }
            System.out.println("---------------");
        }
    }
    private static void frekansa_gore(bagliListee<String> tweets, bagliListee<KelimeSiklik> kelimeSiklikListesi) {
        for (String tweet : tweets) {
            String[] kelimeler = tweet.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

            for (String kelime : kelimeler) {
                KelimeSiklik kelimeSiklik = findWordFrequency(kelime, kelimeSiklikListesi);

                if (kelimeSiklik != null) {
                    kelimeSiklik.sayac();
                } else {
                    kelimeSiklikListesi.ekle(new KelimeSiklik(kelime, 1));
                }
            }
        }
    }

    private static bagliListee<String> findInterestAreas(bagliListee<String> tweets, bagliListee<KelimeSiklik> kelimeSiklikListesi) {
        bagliListee<String> ilgiAlanlari = new bagliListee<>();
        bagliListee<String> kullanicininIlgiAlanlari = new bagliListee<>();
        bagliListee<bagliListee<String>> ilgiAlanlariniTuttugumListe = new bagliListee<>(); // Yeni bağlı liste

        for (String tweet : tweets) {
            String[] kelimeler = tweet.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");

            for (String kelime : kelimeler) {
                KelimeSiklik kelimeSiklik = findWordFrequency(kelime, kelimeSiklikListesi);

                if (kelimeSiklik != null && kelimeSiklik.getSiklik() > 1) {
                    kullanicininIlgiAlanlari.ekle(kelime);
                }
            }
        }

        // Kullanıcının en çok kullandığı kelimeyi bulma
        String enCokKullanilanKelime = most_kelime(kullanicininIlgiAlanlari, kelimeSiklikListesi);

        if (enCokKullanilanKelime != null) {
            ilgiAlanlari.ekle(enCokKullanilanKelime);
            ilgiAlanlariniTuttugumListe.ekle(ilgiAlanlari); // Her bir ilgi alanını yeni bağlı listeye ekle
        }

        return ilgiAlanlari;
    }


    private static String most_kelime(bagliListee<String> words, bagliListee<KelimeSiklik> kelimeSiklikListesi) {
        int maxFrequency = 0;
        String mostUsedWord = null;

        for (String word : words) {
            KelimeSiklik kelimeSiklik = findWordFrequency(word, kelimeSiklikListesi);

            if (kelimeSiklik != null && kelimeSiklik.getSiklik() > maxFrequency) {
                maxFrequency = kelimeSiklik.getSiklik();
                mostUsedWord = kelimeSiklik.getKelime();
            }
        }

        return mostUsedWord;
    }

    private static KelimeSiklik findWordFrequency(String kelime, bagliListee<KelimeSiklik> kelimeSiklikListesi) {
        for (KelimeSiklik kelimeSiklik : kelimeSiklikListesi) {
            if (kelimeSiklik.getKelime().equals(kelime)) {
                return kelimeSiklik;
            }
        }
        return null;
    }
}



class bagliListee<T> implements Iterable<T> {
    private Dugum<T> bas;

    public void ekle(T veri) {
        Dugum<T> yeniDugum = new Dugum<>(veri);
        if (bas == null) {
            bas = yeniDugum;
        } else {
            Dugum<T> temp = bas;
            while (temp.sonraki != null) {
                temp = temp.sonraki;
            }
            temp.sonraki = yeniDugum;
        }
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new BenimIterator();
    }

    public boolean isEmpty() {
        return bas == null;
    }

    private class BenimIterator implements java.util.Iterator<T> {
        private Dugum<T> simdiki = bas;

        @Override
        public boolean hasNext() {
            return simdiki != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T veri = simdiki.veri;
            simdiki = simdiki.sonraki;
            return veri;
        }
    }

    private static class Dugum<U> {
        U veri;
        Dugum<U> sonraki;

        public Dugum(U veri) {
            this.veri = veri;
            this.sonraki = null;
        }
    }

    public T[] toArray(T[] array) {
        int size = size();
        if (array.length < size) {
            // Dizi boyutu yetersizse, yeni bir dizi oluşturun
           /* array = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size);*/
        }

        int i = 0;
        for (T item : this) {
            array[i++] = item;
        }

        return array;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (T item : this) {
            result.append(item).append(", ");
        }
        if (result.length() > 1) {
            result.setLength(result.length() - 2); // Son virgülü ve boşluğu kaldır
        }
        result.append("]");
        return result.toString();
    }

    public boolean contains(T eleman) {
        for (T item : this) {
            if (item.equals(eleman)) {
                return true;
            }
        }
        return false;
    }


    public int size() {
        // Listeyi dolaşarak boyutu hesapla ve döndür
        int count = 0;
        for (T item : this) {
            count++;
        }
        return count;
    }

    public void addAll(bagliListee<T> otherList) {
        for (T item : otherList) {
            this.ekle(item);
        }
    }
}

class KelimeSiklik {
    private String kelime;
    private int siklik;

    public KelimeSiklik(String kelime, int siklik) {
        this.kelime = kelime;
        this.siklik = siklik;
    }

    public String getKelime() {

        return kelime;
    }

    public int getSiklik() {
        return siklik;
    }

    public void sayac() {
        siklik++;
    }
}


