RevoGuild
=========

#### W przyszłości bardziej opiszę cały plugin, tymczasem musisz zadowolić się tym opisem. Jeśli będziesz miał jakiś problem pisz na GG: 37846242

Rewolucyjny system gildii na Twój serwer! ;)

#### Konfiguracja (plik config.yml):
````yaml
config:
  enabled: false #Aktywnosc pluginu: true/false
  useuuid: true #Uzywanie UUID jako identyfikatora gracza: true/false (jesli nie korzystasz z wersji powyżej 1.7.9 ustaw false)
  database:
    mode: sqlite #Tryb bazy danych: sqlite/mysql
    tableprefix: ks_
    mysql:
      host: localhost
      port: 3306
      user: root
      pass: ''
      name: minecraft
    sqlite:
      name: minecraft.db
  tag:
    mode: tagapi #Tryb tagów nad głową: tagapi/scoreboard
    format: '&8[{COLOR}{TAG}&8] {COLOR}' #Format tagu nad głową
    color:
      noguild: '&7' #Brak gildii
      friend: '&a' #Własna gildia
      friendpvp: '&9' #Własna gildia z włączonym pvp
      enemy: '&c' #Wróg
      alliance: '&6' #Sojusznik
  chat:
    format:
      tag: '&8[&2{TAG}&8]&r ' #Format tagu gildii na czacie
      rank: '&8[&2{RANK}&8]&r ' #Format rankignu gracza na czacie
      tagdeathmsg: '&7[&2{TAG}&7]&r ' #Format tagu gildii w wiadomosci od śmierci
  ranking:
    startpoints: 1000 #Początkowa ilość punktów rankingu
    deathmessage: '&2Gracz {PGUILD} &7{PLAYER} ({LOSEPOINTS}) &2zostal zabity przez {KGUILD} &7{KILLER} ({WINPOINTS})&2!' #Wiadomosc po śmierci
  escape:
    pointsremove: 50 #Ilość punktów usuwana podczas ucieczki z pvp
    broadcast: '&2Gracz {PGUILD} &7{PLAYER} &7(-50) &2wylogowal sie podczas walki!' #Wiadomosc po ucieczce
  algorithm:
    ranking:
      win: (300 + (({KILLER_POINTS} - {PLAYER_POINTS}) * (-0.2))) #Algorytm obliczania rankingu dla zwycięzcy pojedynku
      lose: Math.abs({WIN_POINTS}/2) #Algorytm obliczania rankingu dla przegranego pojedynku
    guild:
      points: '{MEMBERS_POINTS} - ({MEMBERS_NUM}*1000)' #Algorytm obliczania rankingu gildii
    enlarge: ({CUBOID_SIZE} - 24)/5 +1 #Algorytm określający ilosć pobieranych itemów podczas powiększania
  actions:
    block:
      break: false #Mozliwosc niszczenie terenu gildii
      place: false #Mozliwosc budowania na terenie gildii
    bucket:
      empty: false #Mozliwosc opróżniania wiadra na terenie gildii
      fill: false #Mozliwosc napełniania wiadra na terenie gildii
    protectedid: #Id bloków, których nie można używać na terenie gildii
    - 54
  uptake:
    enabled: false #Mozliwosc przejmowania gildii
    lives:
      start: 3 #Poczatkowa ilosc zyc
      max: 6 #Maksymalna ilosc zyc
      time: 24 #Czas pomiedzy przejęciem życia gildii (w godzinach)
  treasure:
    enabled: false #Mozliwosc korzystania ze skarbcow gildii
    openonlyonguild: true #Mozliwosc otwierania skarbca tylko na terenie gildii
    title: 'Skarbiec gildii #Nazwa ekwipunku skarbca
    rows: 6 #Ilosc wierszy ekwipunku skarbca
  tnt:
    'off':
      enabled: false #Mozliwosc wylaczanie TNT w godzinach podanych nizej
      hours:
      - 0
      - 1
      - 2
      - 3
      - 4
      - 5
      - 6
      - 7
      - 8
    protection:
      enabled: false #Mozliwosc ochrony nowej gildii przed TNT
      time: 24 #Czas ochrony przez TNT (w godzinach)
    cantbuild:
      enabled: false #Nie możność budowania po wybuchu TNT na tereni gildii
      time: 90 #Czas blokady budowania (w sekundach)
    durability:
      enabled: false #Mozliwosc zmiany wytrzymalosci blokow (np. możliwość niszczenia obsydianu poprzez TNT)
      blocks: #Lista blokow; format: '<nazwa bloku> <wytrzymalosc>'
      - OBSIDIAN 73.6
      - WATER 10.0
      - STATIONARY_WATER 10.0
  cost: #Ilość przedmiotów potrzebnych do poszczególnych akcji. Normal - użytkownik bez uprawnienia revoguild.vip; vip - użytkownik z uprawnieniem revoguild.vip; format: '<id>:<subid>-<ilosc>;<id>:<subid>-<ilosc>;<id>:<subid>-<ilosc>;'
    create: #Tworzenie gildii
      normal: 1:0-10;
      vip: 1:0-5;
    join: #Dołączanie do gildii
      normal: 1:0-10;
      vip: 1:0-5;
    leader: #Zmiana lidera gildii
      normal: 1:0-10;
      vip: 1:0-5;
    owner: #Zmiana założyciela gildii
      normal: 1:0-10;
      vip: 1:0-5;
    enlarge: #Powiększanie gildii
      normal: 1:0-10;
      vip: 1:0-5;
    prolong: #Przedłużanie gildii
      normal: 1:0-10;
      vip: 1:0-5;
  cuboid: 
    world: world #Świat, w którym możemy stworzyć gildię
    size: #Rozmiary gildii
      start: 24 #Początkowy rozmiar (promień!)
      max: 74 #Maksymalny rozmiar (promień!)
      add: 1 #Wartosc o jaką jest powiększana gildia
      between: 50 #Odległość pomiędzy gildiami powiększonymi maksymalnie
    spawn:
      distance: 400 #Odległość od spawna (0,0)
  time:
    start: 3 #Poczatkowa ważność gildii (w dniach)
    max: 14 #Maksymalna ważność gildii (w dniach)
    add: 7 #Wartość o jaką jest przedłużana gildia (w dniach)
    check: 3 #Odstęp pomiędzy sprawdzaniem ważnościem gildii (w godzinach)
    teleport: 10 #Czas do teleport (w sekundach)
  tablist:
    enabled: false #Mozliwosc korzystania z customowej tablisty
    refresh:
      interval: 1 #Czas do odświeżenia wartości pól wyróżnionych w tablist.yml -> update-slots

````

*Opiszę w wolnej w chwili ;)*

=======
#### Konfiguracja tablisty (plik tablist.yml):
````yaml
tablist:
  slots:
    '5': ' &2MC.|&7&lKAROLEK|&8.NET'
    '13': '  &2|Ranking graczy|'
    '14': '     &2|INFORMACJE:|'
    '15': '     &2|Ranking gildii|'
    '16': '&7|1.&2 |{PTOP-1}'
    '17': '&7|Godzina: |&2{TIME}'
    '18': '&7|1.&r&2 |{GTOP-1}'
    '19': '&7|2.&2 |{PTOP-2}'
    '20': '&7|Zabójstwa: |&2{KILLS}'
    '21': '&7|2.&r&2 |{GTOP-2}'
    '22': '&7|3.&2 |{PTOP-3}'
    '23': '&7|Smierci: |&2{DEATHS}'
    '24': '&7|3.&r&2 |{GTOP-3}'
    '25': '&7|4.&2 |{PTOP-4}'
    '26': '&7|Punkty: |&2{POINTS}'
    '27': '&7|4.&r&2 |{GTOP-4}'
    '28': '&7|5.&2 |{PTOP-5}'
    '29': '&7|Gildia: |&2{TAG}'
    '30': '&7|5.&r&2 |{GTOP-5}'
    '31': '&7|6.&2 |{PTOP-6}'
    '32': '&7|Ping: |&2{PING}&7 ms'
    '33': '&7|6.&r&2 |{GTOP-6}'
    '34': '&7|7.&2 |{PTOP-7}'
    '35': '&7|Online: |&2{ONLINE}/300'
    '36': '&7|7.&r&2 |{GTOP-7}'
    '37': '&7|8.&2 |{PTOP-8}'
    '39': '&7|8.&r&2 |{GTOP-8}'
    '40': '&7|9.&2 |{PTOP-9}'
    '42': '&7|9.&r&2 |{GTOP-9}'
    '43': '&7|10.&2 |{PTOP-10}'
    '45': '&7|10.&r&2 |{GTOP-10}'
    '52': '     &7|Facebook:|'
    '53': '    &7|TeamSpeak3:|'
    '54': '     &7|Strona WWW:|'
    '55': '&2|fb.com/|karolek.net'
    '56': '    &2|ts.karolek.net|'
    '57': '&2|http://karolek|.net'
  update-slots:
  - 17
  - 32
  - 35
````

Zapis ` 45: '&7|10.&r&6 |{GTOP-10}'` oznacza, że w 45 slocie zobaczymy właśnie taki tekst. Pustych slotów nie musimy definiować. Jeśli wartość w którymś slocie się powtórzy to dostaniemy crasha.

W sekcji `update-slots` wpisujemy ID slotów, które mają być aktualizowane w sposób ciągły (np. czas lub ping). Wszystkie wartości dotyczące rankingu gildii/graczy aktualizowane są wtedy, gdy się zmienią.

`PREFIX|NAME|SUFFIX` - prefix i suffix zmieniają się podczas odświeżania taba, name natomiast jest taki sam i reprezentuje dany team.

=======
