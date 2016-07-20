package sample.additions;

import java.util.ArrayList;

import static sample.additions.Codes.CODE_EDITOR_CONNECTED;
import static sample.additions.Codes.CODE_EDITOR_ERROR_AUTHORIZATION;

/**
 * Created by Mashenkin Roman on 18.07.2016.
 */
public class Utils {

//    public static ArrayList<String> initLeague(String leagues) {
//
//        ArrayList<String> listLeague = new ArrayList<>();
//
//        listLeague.add("English Premier League");
//        listLeague.add("Spanish La Liga");
//        listLeague.add("Italian Serie A");
//        listLeague.add("French Ligue 1");
//        listLeague.add("German Bundesliga 1");
//        listLeague.add("Russian Premier League");
//
//        return listLeague;
//    }
//
//    public static ArrayList<String> initSeason() {
//
//        ArrayList<String> season = new ArrayList<>();
//
//        season.add("2016/2017");
//
//        return season;
//    }

    public static ArrayList<String> initEPL(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> EPL = new ArrayList<>();

//        EPL.add("Arsenal");
//        EPL.add("Bournemouth");
//        EPL.add("Burnley");
//        EPL.add("Chelsea");
//        EPL.add("Crystal Palace");
//        EPL.add("Everton");
//        EPL.add("Hull City");
//        EPL.add("Leicester City");
//        EPL.add("Liverpool");
//        EPL.add("Manchester City");
//        EPL.add("Manchester United");
//        EPL.add("Middlesbrough");
//        EPL.add("Southampton");
//        EPL.add("Stoke City");
//        EPL.add("Sunderland");
//        EPL.add("Swansea City");
//        EPL.add("Tottenham Hotspur");
//        EPL.add("Watford");
//        EPL.add("West Bromwich Albion");
//        EPL.add("West Ham United");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 1) {
                EPL.add(team.getValue());
            }

        return EPL;
    }

    public static ArrayList<String> initSLL(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> SLL = new ArrayList<>();

//        SLL.add("Alavés");
//        SLL.add("Athletic Bilbao");
//        SLL.add("Atlético Madrid");
//        SLL.add("Barcelona");
//        SLL.add("Celta Vigo");
//        SLL.add("Deportivo La Coruña");
//        SLL.add("Eibar");
//        SLL.add("Eibar");
//        SLL.add("Granada");
//        SLL.add("Las Palmas");
//        SLL.add("Leganés");
//        SLL.add("Málaga");
//        SLL.add("Osasuna");
//        SLL.add("Real Betis");
//        SLL.add("Real Madrid");
//        SLL.add("Real Sociedad");
//        SLL.add("Sevilla");
//        SLL.add("Sporting Gijón");
//        SLL.add("Valencia");
//        SLL.add("Villarreal");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 2) {
                SLL.add(team.getValue());
            }

        return SLL;
    }

    public static ArrayList<String> initISA(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> ISA = new ArrayList<>();

//        ISA.add("Atalanta");
//        ISA.add("Bologna");
//        ISA.add("Cagliari");
//        ISA.add("Chievo");
//        ISA.add("Crotone");
//        ISA.add("Empoli");
//        ISA.add("Fiorentina");
//        ISA.add("Genoa");
//        ISA.add("Internazionale");
//        ISA.add("Juventus");
//        ISA.add("Lazio");
//        ISA.add("Milan");
//        ISA.add("Napoli");
//        ISA.add("Palermo");
//        ISA.add("Pescara");
//        ISA.add("Roma");
//        ISA.add("Sampdoria");
//        ISA.add("Sassuolo");
//        ISA.add("Torino");
//        ISA.add("Udinese");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 3) {
                ISA.add(team.getValue());
            }

        return ISA;
    }

    public static ArrayList<String> initFL1(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> FL1 = new ArrayList<>();

//        FL1.add("Angers");
//        FL1.add("Bastia");
//        FL1.add("Bordeaux");
//        FL1.add("Caen");
//        FL1.add("Dijon");
//        FL1.add("Guingamp");
//        FL1.add("Lille");
//        FL1.add("Lorient");
//        FL1.add("Lyon");
//        FL1.add("Marseille");
//        FL1.add("Metz");
//        FL1.add("Monaco");
//        FL1.add("Montpellier");
//        FL1.add("Nancy");
//        FL1.add("Nantes");
//        FL1.add("Nice");
//        FL1.add("Paris Saint-Germain");
//        FL1.add("Rennes");
//        FL1.add("Saint-Étienne");
//        FL1.add("Toulouse");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 4) {
                FL1.add(team.getValue());
            }

        return FL1;
    }

    public static ArrayList<String> initGB1(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> GB1 = new ArrayList<>();

//        GB1.add("FC Augsburg");
//        GB1.add("Bayer Leverkusen");
//        GB1.add("Bayern Munich");
//        GB1.add("Borussia Dortmund");
//        GB1.add("Borussia Mönchengladbach");
//        GB1.add("Darmstadt 98");
//        GB1.add("Eintracht Frankfurt");
//        GB1.add("SC Freiburg");
//        GB1.add("Hamburger SV");
//        GB1.add("Hertha BSC");
//        GB1.add("TSG Hoffenheim");
//        GB1.add("FC Ingolstadt");
//        GB1.add("1. FC Köln");
//        GB1.add("RB Leipzig");
//        GB1.add("Mainz 05");
//        GB1.add("Schalke 04");
//        GB1.add("Werder Bremen");
//        GB1.add("VfL Wolfsburg");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 5) {
                GB1.add(team.getValue());
            }

        return GB1;
    }

    public static ArrayList<String> initRPL(ArrayList<Pair<Integer, String>> teams) {

        ArrayList<String> RPL = new ArrayList<String>();

//        RPL.add("Amkar");
//        RPL.add("Anzhi");
//        RPL.add("Arsenal");
//        RPL.add("CSKA");
//        RPL.add("Gazovik Orenburg");
//        RPL.add("Krasnodar");
//        RPL.add("Krylia Sovetov");
//        RPL.add("Lokomotiv");
//        RPL.add("Rostov");
//        RPL.add("Rubin");
//        RPL.add("Spartak Moscow");
//        RPL.add("Terek");
//        RPL.add("Tom");
//        RPL.add("Ufa");
//        RPL.add("Ural");
//        RPL.add("Zenit");

        for (Pair<Integer, String> team : teams)
            if (team.getCode() == 6) {
                RPL.add(team.getValue());
            }

        return RPL;
    }

    public static String analise(String line) {

        ArrayList<Pair<String, String>> messages = decoder(line);
        String code = "";

        for (Pair<String, String> message : messages) {
            if (message.getCode().equals(CODE_EDITOR_CONNECTED) ||
                    message.getCode().equals(CODE_EDITOR_ERROR_AUTHORIZATION))
                code = message.getCode();
        }

        return code;
    }

    private static ArrayList<Pair<String, String>> decoder(String line) {

        ArrayList<Pair<String, String>> messages = new ArrayList<>();
        int j = 0;

        for (int i = 0; i < line.length(); i++) {

            if (line.charAt(i) != '|') continue;

            messages.add(new Pair<>(line.substring(j, j + 3),
                    ((line.length() >= i + 1) ? line.substring(j + 3, i) : "")));
            j = i + 1;
        }

        return messages;
    }
}
