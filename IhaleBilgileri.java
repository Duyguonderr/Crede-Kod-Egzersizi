package ihalescrapper;

class IhaleBilgileri {
    private String ihaleKayitNo;
    private String niteligiTuruMiktari;
    private String yer;
    private String tur;
    private String url;

    public IhaleBilgileri(String ihaleKayitNo, String niteligiTuruMiktari, String yer, String tur, String url) {
        this.ihaleKayitNo = ihaleKayitNo;
        this.niteligiTuruMiktari = niteligiTuruMiktari;
        this.yer = yer;
        this.tur = tur;
        this.url = url;
    }

    public String toString(){
        return  "İhale Kayıt No: " + ihaleKayitNo + "\n" +
                "Niteliği, Türü, Miktarı: " + niteligiTuruMiktari + "\n" +
                "İşin Yapılacağı Yer: " + yer + "\n" +
                "İhale Türü: " + tur + "\n" +
                "Url: " + url + "\n\n" ;
    }
}
