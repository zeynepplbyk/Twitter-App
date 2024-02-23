import json
import random
from faker import Faker

fake = Faker()

def hesap_ozellikleri(existing_usernames):
    takipçi_sayi = fake.random_int(10, 50)
    takip_count = fake.random_int(10, 50)
    tweet_sayi = fake.random_int(30, 40)

    username = fake.user_name() #ayni isimden baska varsa bir daha olsutur 
    while username in existing_usernames:
        username = fake.user_name()

    existing_usernames.add(username)

    user_data = {
        "username": username,
        "name": fake.name(),
        "followers.count": takipçi_sayi,
        "following.count": takip_count,
        "language": "tr",
        "region": "TR",
        "tweets": [fake.sentence() for _ in range(tweet_sayi)],
        "following": [],
        "followers": [],
    }

    return user_data

def generate_users_with_followers(user_count):
    kullanicilar = []
    existing_usernames = set()

    for _ in range(user_count):
        user = hesap_ozellikleri(existing_usernames)
        kullanicilar.append(user)

    for user in kullanicilar:
        max_followers = min(user_count - 1, user["followers.count"])
        user["followers"] = [other_user["username"] for other_user in random.sample(kullanicilar, max_followers) if other_user["username"] != user["username"]]

    for user in kullanicilar:
        max_following = min(user_count - 1, user["following.count"])
        user["following"] = [other_user["username"] for other_user in random.sample(kullanicilar, max_following) if other_user["username"] != user["username"]]

    return kullanicilar

all_users = generate_users_with_followers(300)

with open("tum_kullanicilar.json", "w", encoding="utf-8") as file:
    json.dump(all_users, file, ensure_ascii=False, indent=2)



print("Tüm kullanıcı verileri tum_kullanicilar.json dosyasina  yazildi.")
