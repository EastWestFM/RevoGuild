RevoGuild
=========

Rewolucyjny system gildii na Twój serwer! ;)

###### Lista TODO:
- [x] komendy administratora
- [x] wsparcie dla pluginów od czatu
- [ ] czat gildyjny oraz sojuszniczy
- [x] wsparcie dla pluginów rankingowych
- [x] tablista
- [ ] optymalizacja! :smile:

========
#### Komendy:

Komenda|Uprawnienie|Opis działania
:-------------|:-------------:|:-------------
/g (subkomenda)|rg.cmd.user|główna komenda systemu gildii
/zaloz (tag) (nazwa)|rg.cmd.user.create|tworzenie gildii
/usun|rg.cmd.user.delete|usuwanie gildii
/sojusz (tag/nazwa)|rg.cmd.user.alliance|zarzadzanie sojuszami gildii
/zapros (gracz)|rg.cmd.user.invite|zapraszanie gracza do gildii
/dolacz (tag/nazwa)|rg.cmd.user.join|dolaczanie do gildii
/wyrzuc (gracz)|rg.cmd.user.kick|wyrzucanie gracza z gildii
/opusc|rg.cmd.user.leave|opuszczanie gildii
/skarbiec [dodaj (gracz) / usun (gracz) / lista]|rg.cmd.user.treasure|zarzadzanie skarbcem gildii
/dom|rg.cmd.user.home|teleportowanie do domu gildii
/ustawdom|rg.cmd.user.sethome|ustawianie domu gildii
/pvp|rg.cmd.user.pvp|zmienianie statusu pvp w gildii
/info (tag/nazwa)|rg.cmd.user.info|wyswietlanie podstawowych informacji o gidlii
/lider (gracz)|rg.cmd.user.leader|zmienianie lidera gildii
/zalozyciel (gracz)|rg.cmd.user.owner|zmienianie zalozyciela gildii
/lista|rg.cmd.user.list|wyswietlanie listy wszystkich gildii
/powieksz|rg.cmd.user.enlarge|powiekszanie terenu gildii
/przedluz|rg.cmd.user.prolong|przedluzanie waznosci gildii


Wszystkie komendy mogą być wykonywane jako **subkomenda** do komendy `/g` (przykład: `/g zaloz (tag) (nazwa)`) lub jako **osobne**, **indywidualne** komendy (przykład: `/zaloz (tag) (nazwa)`)

========
#### Komendy administratora:

Komenda|Uprawnienie|Opis działania
:-------------|:-------------:|:-------------
/ga (subkomenda)|rg.cmd.admin|główna komenda 
/ga usun (tag/nazwa)|rg.cmd.admin.delete|usuwanie gildii
/ga tp (tag/nazwa|rg.cmd.admin.tp|teleport do gildii
/ga reload|rg.cmd.admin.reload|przeladowanie plikow konfiguracyjnych

========
#### Komendy dot. rankingu:

Komenda|Uprawnienie|Opis działania
:-------------|:-------------:|:-------------
/ranking [gracz]|rg.cmd.user.ranking|wyświetlanie rankingu gracza 
/top|rg.cmd.user.top|lista top10 graczy



=======
#### Zmienne (plik lang.yml):

Komenda|Opis działania
:-------------|:-------------
{TAG}|tag gildii
{NAME}|nazwa gildii
{OWNER}|zalozyciel gildii
{LEADER}|lider gildii
{CREATETIME}|data utworzenia gildii
{EXPIRETIME}|data wygasniecia gildii
{SIZE}|rozmiar gildii
{MEMBERS}|czlonkowie gildii
{MEMBERNUM}|liczba czlonkow w gildii
{ONLINENUM}|liczba czlonkow online w gildii
{PLAYER}|nick gracza

Istnieją również zmienne dotyczące drugiej gildii, jednak ze względu na ich budowę pokażę tylko schemat: `{TAG2}` - zwróci nam tag drugiej gildii.

======
#### Konfiguracja (plik config.yml):
````yaml
config:
  enabled: false
  useuuid: true
  database:
    mode: mysql
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
    mode: tagapi
    format: '&8[{COLOR}{TAG}&8] {COLOR}'
    color:
      noguild: '&7'
      friend: '&a'
      friendpvp: '&9'
      enemy: '&c'
      alliance: '&6'
  chat:
    format:
      tag: '&8[&2{TAG}&8]&r '
      rank: '&8[&2{RANK}&8]&r '
      tagdeathmsg: '&7[&2{TAG}&7]&r '
  ranking:
    startpoints: 1000
    deathmessage: '&2Gracz {PGUILD} &7{PLAYER} ({LOSEPOINTS}) &2zostal zabity przez
      {KGUILD} &7{KILLER} ({WINPOINTS})&2!'
    algorithm:
      win: (300 + (({KILLER_POINTS} - {PLAYER_POINTS}) * (-0.2)))
      lose: Math.abs({WIN_POINTS}/2)
  enlarge:
    algorithm: ({CUBOID_SIZE} - 24)/5 +1
  actions:
    block:
      break: false
      place: false
    bucket:
      empty: false
      fill: false
    protectedid:
    - 54
  uptake:
    enabled: false
    lives:
      start: 3
      max: 6
      time: 24
  treasure:
    enabled: false
    title: 'Skarbiec gildii:'
    rows: 6
  tnt:
    'off':
      enabled: false
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
      enabled: false
      time: 24
    cantbuild:
      enabled: false
      time: 90
    durability:
      enabled: false
      blocks:
      - OBSIDIAN 73.6
      - WATER 10.0
      - STATIONARY_WATER 10.0
  cost:
    create: 1:0-10;
    join: 1:0-10;
    leader: 1:0-10;
    owner: 1:0-10;
    enlarge: 1:0-10;
    prolong: 1:0-10;
  size:
    start: 24
    max: 74
    add: 1
    between: 50
  time:
    start: 3
    max: 14
    add: 7
    check: 3
    teleport: 10
  tablist:
    enabled: false
    refresh:
      interval: 1
    slots: in tablist.yml
````

*Opiszę w wolnej w chwili ;)*

=======
#### Konfiguracja tablisty (plik tablist.yml):

Przykładowa konfiguracja:
````yaml
tablist:
  5: '&7|&6&lCRAFT&7|&l.PL'
  13: '&2&l|TOP GRACZY:|'
  14: '&2&l|INFORMACJE:|'
  15: '&2&l|TOP GILDIE:|'
  16: '&7|1.&6 |{PTOP-1}'
  17: '&7|Godzina: |&c{TIME}'
  18: '&7|1.&r&6 |{GTOP-1}'
  19: '&7|2.&6 |{PTOP-2}'
  20: '&7|Zabójstwa: |&c{KILLS}'
  21: '&7|2.&r&6 |{GTOP-2}'
  22: '&7|3.&6 |{PTOP-3}'
  23: '&7|Smierci: |&c{DEATHS}'
  24: '&7|3.&r&6 |{GTOP-3}'
  25: '&7|4.&6 |{PTOP-4}'
  26: '&7|Punkty: |&c{POINTS}'
  27: '&7|4.&r&6 |{GTOP-4}'
  28: '&7|5.&6 |{PTOP-5}'
  29: '&7|Gildia: |&c{TAG}'
  30: '&7|5.&r&6 |{GTOP-5}'
  31: '&7|6.&6 |{PTOP-6}'
  32: '&7|Ping: |&c{PING}'
  33: '&7|6.&r&6 |{GTOP-6}'
  34: '&7|7.&6 |{PTOP-7}'
  35: '&7|Online: |&c{ONLINE}/300'
  36: '&7|7.&r&6 |{GTOP-7}'
  37: '&7|8.&6 |{PTOP-8}'
  39: '&7|8.&r&6 |{GTOP-8}'
  40: '&7|9.&6 |{PTOP-9}'
  42: '&7|9.&r&6 |{GTOP-9}'
  43: '&7|10.&6 |{PTOP-10}'
  45: '&7|10.&r&6 |{GTOP-10}'
````

Zapis ` 45: '&7|10.&r&6 |{GTOP-10}'` oznacza, że w 45 slocie zobaczymy właśnie taki tekst. Pustych slotów nie musimy definiować. Jeśli wartość w którymś slocie się powtórzy to dostaniemy crasha.

`PREFIX|NAME|SUFFIX` - prefix i suffix zmieniają się podczas odświeżania taba, name natomiast jest taki sam i reprezentuje dany team.

=======
