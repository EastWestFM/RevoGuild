RevoGuild
=========

Rewolucyjny system gildii na Twój serwer! ;)

###### Lista TODO:
[ ]komendy administratora
[ ]wsparcie dla pluginów od czatu
[ ]czat gildyjny oraz sojuszniczy
[ ]wsparcie dla pluginów rankingowych
[ ]tablista
[ ]optymalizacja! :smile:

========
#### Komendy:

Komenda|Permisja|Opis działania
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
  enabled: true #Czy plugin ma byc aktywny: true/false
  database:
    mode: mysql #Tryb bazy danych: mysql/sqlite
    tableprefix: ks_ #Prefix tabel bazy danych
    mysql:
      host: 185.5.96.166 #Host bazy danych MySQL
      port: 3306 #Port bazy danych MySQL
      user: sid1276_c4u #Użytkownik bazy danych MySQL
      pass: ZAQ!2wsx #Hasło użytkownika bazy danych MySQL
      name: sid1276_c4u #Nazwa bazy danych MySQL
    sqlite:
      name: minecraft.db #Nazwa bazy danych SQLite
  tag:
    mode: tagapi #Tryb tagów nad głową gracza: tagapi/scoreboard (polecam wybór opcji tagapi (wymagany plugin TagAPI) ze względu na wydajność)
    format: '&8[{COLOR}{TAG}&8] {COLOR}' #Format tagu gracza. {COLOR} oznacza kolor rejacji
    color: #Kolory poszczegolnych relacji
      noguild: '&7' #Brak gildii
      friend: '&a' #Członek naszej gildii
      friendpvp: '&9' #Członek naszej gildii, w której pvp jest włączone
      enemy: '&c' #Wróg
      alliance: '&6' #Sojusznik
  uptake: 
    enabled: false #Mozliwosc przejmowania gildii: true/false
    lives: 
      amount: 3 #Ilosc zyc gildii
      time: 24 #Czas pomiedzy przejmowaniem zycia gildii przez inna gildie. W godzinach.
  treasure:
    enabled: false #Mozliwosc korzystania ze skarbca gildii: true/false
    title: 'Skarbiec gildii:' #Nazwa skarbca gildii
    rows: 6 #Ilosc wierszy ekwipunku skarbca
  tnt:
    'off':
      enabled: false #Czy tnt ma byc wylaczone w podanych godzinach: true/false
      hours: #Godziny w ktorych TNT jest nie aktywne
      - 0
      - 1
      - 2
      - 3
      - 4
      - 5
      - 6
      - 7
      - 8
    cantbuild:
      enabled: false #Mozliwosc budowania po wybuchu tnt: true/false
      time: 90 #Czas, po którym mozna budowac
    durability:
      enabled: false #Mozliwosc zmiany wytrzymalosci blokow podczas wybuchu TNT: true/false
      blocks: #Lista blokow: <NAZWA_BLOKU> WYTRZYMALOSC
      - OBSIDIAN 73.6
      - WATER 10.0
      - STATIONARY_WATER 10.0
  cost: #Koszt wykonania poszzegolnych akcji na serwerze. Zapis 1:0-10; oznacza, że potrzebujemy 10 stone. Aby dodać kolejny przedmiot wystarczy dopisac <id>:<subid>-ilosc; po sredniku
    create: 1:0-10; #Tworzenie gildii
    join: 1:0-10; #Dolaczanie do gildii
    leader: 1:0-10; #Zmiana lidera gildii
    owner: 1:0-10; #Zmiana zalozyciela gildii
    enlarge: 1:0-10; #Powiekszanie gildii
    prolong: 1:0-10; #Przedluzanie gildii
  size: #Rozmiar (promien) cuboida gildii
    start: 24 #Poczatkowy rozmiar
    max: 74 #Maksymalny romiar
    add: 1 #Dodowana ilosc podczas powiekszania
    between: 50 #Odstep pomiedzy powiekszonymi maksymalnie gildiami
  time: #Czas waznosci gildii
    start: 3 #Poczatkowy czas (dni)
    max: 14 #Maksymalny czas (dni)
    add: 7 #Dodawana ilosc podczas przedluzania (dni)
    check: 3 #Czas sprawdzania waznosci gildii (godziny)
    teleport: 10 #Czas oczekiwania na teleport (sekundy)

````

=======
