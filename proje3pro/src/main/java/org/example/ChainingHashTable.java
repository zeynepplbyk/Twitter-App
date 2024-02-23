package org.example;
import java.io.FileWriter;
import java.io.IOException;
// ZİNCİRLEME
public class ChainingHashTable {
    private bagliListee<User>[] listem; // bağlı liste kullanıcı tipinde verileri içerir

    public ChainingHashTable(int size) {
        this.listem = new bagliListee[size];
        for (int i = 0; i < size; i++) {
            listem[i] = new bagliListee<>();
        }
    }

    public void put(User user) {
        int index = hash(user.username);
        listem[index].ekle(user);
    }

    public User get(String username) {
        int index = hash(username);
        bagliListee<User> userList = listem[index];
        for (User user : userList) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void ekranayaz() {
        for (bagliListee<User> userList : listem) {
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    public void iliskiler() {
        for (bagliListee<User> userList : listem) {
            for (User user : userList) {
                for (String followingName : user.followingNames) {
                    User followingUser = get(followingName);
                    if (followingUser != null) {
                        user.addFollowing(followingUser);
                    }
                }

                for (String followerName : user.followerNames) {
                    User followerUser = get(followerName);
                    if (followerUser != null) {
                        user.addFollower(followerUser);
                    }
                }
            }
        }
    }

    public void Graph_cikti() {
        for (bagliListee<User> userList : listem) {
            for (User user : userList) {
                System.out.print(user.username + " -> ");
                for (User followingUser : user.followingUsers) {
                    System.out.print(followingUser.username + " ");
                }
                System.out.println();
                System.out.print("followuser: ");
                for (User followerUser : user.followerUsers) {
                    System.out.print(followerUser.username + " ");
                }
                System.out.println("\n");
            }
        }
    }



    public void dosya(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (bagliListee<User> userList : listem) {
                for (User user : userList) {
                    writer.write(user.username + " -> ");
                    for (User followingUser : user.followingUsers) {
                        writer.write(followingUser.username + " ");
                    }
                    writer.write("\n");
                    writer.write("followuser: ");
                    for (User followerUser : user.followerUsers) {
                        writer.write(followerUser.username + " ");
                    }
                    writer.write("\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private int hash(String key) {
        return key.length() % listem.length;
    }

    public static class User {
        String username;
        String name;
        int followersCount;
        int followingCount;
        String language;
        String region;
        bagliListee<String> tweets; // Tweet listesi
        bagliListee<String> followerNames; // Takipçi isimleri listesi
        bagliListee<String> followingNames; // Takip edilen isimleri listesi
        bagliListee<User> followingUsers; // Kullanıcının takip ettiği kişilerin listesi
        bagliListee<User> followerUsers; // Kullanıcının kendisini takip eden kişilerin listesi

        public User(String username, String name, int followersCount, int followingCount, String language, String region, bagliListee<String> tweets, bagliListee<String> followerNames, bagliListee<String> followingNames) {
            this.username = username;
            this.name = name;
            this.followersCount = followersCount;
            this.followingCount = followingCount;
            this.language = language;
            this.region = region;
            this.tweets = tweets;
            this.followerNames = followerNames;
            this.followingNames = followingNames;
            this.followingUsers = new bagliListee<>();
            this.followerUsers = new bagliListee<>();
        }

        public void addFollowing(User followingUser) {
            followingUsers.ekle(followingUser);
        }

        public void addFollower(User followerUser) {
            followerUsers.ekle(followerUser);
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("Kullanıcı Adı: " + username +
                    "\nAdı: " + name +
                    "\nTakipçi Sayısı: " + followersCount +
                    "\nTakip Edilen Sayısı: " + followingCount +
                    "\nDil: " + language +
                    "\nBölge: " + region +
                    "\nTweetler: [" + tweets +
                    "]\nTakipçi İsimleri: " + followerNames +
                    "\nTakip Edilen İsimleri: " + followingNames +
                    "\n------------------------");
            return result.toString();
        }

        public String getFollowerNames() {
            StringBuilder followers = new StringBuilder();
            for (String follower : followerNames) {
                followers.append(follower).append(" ");
            }
            return followers.toString().trim();

        }

        public String getFollowingNames() {
            StringBuilder following = new StringBuilder();
            for (String followingName : followingNames) {
                following.append(followingName).append(" ");
            }
            return following.toString().trim();

        }
    }


}
