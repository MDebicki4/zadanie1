Feature: Zadanie testowe 1

  Scenario: Wybor telefonu z oferty bez abonamentu
    Given Otworz przegladarke
    Then Przegladarka otworzona

    Given Przejdz na strone "https://www.t-mobile.pl/"
    Then Strona glowna jest widoczna

    Given Z gornej belki wybierz "Urządzenia"
    Then Widoczna rozwijana lista

    When Kliknij "Smartwatche" z kolumny Bez abonamentu
    Then Widoczna lista smartfonow

    When Kliknij w pierwszy element z listy
    Then Widoczna strona produktu

    When Dodaj produkt do koszyka
    Then Widoczna strona Twoj koszyk
    And Kwoty Cena na start oraz Rata miesieczna zgadzaja sie z kwotami z poprzedniej strony

    When Przejdz na strone glowna "https://www.t-mobile.pl/"
    Then Widoczna strona glowna
    And W prawym gornej rogu widoczna jest ikonka koszyka z liczba produktow w koszyku