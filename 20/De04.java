import java.util.*;

public class De04 {
    public static void main(String[] args) {
        List<String> inputLines = ReadFile.getTextFromFile("20/input04.txt");
        System.out.println(inputLines);
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        documents.add(document);
        for (String line: inputLines){
            if (line.isEmpty()){
                document = new Document();
                documents.add(document);
                continue;
            }
            document.addInputLine(line);
        }
        System.out.println(documents.size());
        int count = 0;
        for (Document d: documents){
            if (d.isValid()){
                count++;
            }
        }
        System.out.println("Valids: " + count);
    }
}

class Document {
    private final List<String> reqFields = new ArrayList<>(List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
    private final List<String> optionalFields = new ArrayList<>(List.of("cid"));
    private final List<String> eyeColor = new ArrayList<>(List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
    private Map<String, String> fields;

    public Document() {
        this.fields = new HashMap<String, String>();
    }

    public void addInputLine(String input){
        String[] field;
        for(String line: input.split(" ")){
            field = line.split(":");
            if (reqFields.contains(field[0]) || (optionalFields.contains(field[0]))){
                this.fields.put(field[0], field[1]);
            }
        }
    }

    public boolean isValid(){
        for (String key: this.reqFields){
            if (this.fields.containsKey(key)){
            } else {
                return false;
            }
        }
        System.out.println(validateByr());
        System.out.println(validateEcl());
        System.out.println(validateEyr());
        System.out.println(validateHcl());
        System.out.println(validateHgt());
        System.out.println(validateIyr());
        System.out.println(validatePid());
        return validateByr() &&
                validateEcl() &&
                validateEyr() &&
                validateHcl() &&
                validateHgt() &&
                validateIyr() &&
                validatePid();
    }

    private boolean validatePid(){
        try {
            int pid = Integer.parseInt(this.fields.get("pid"));
            System.out.println(pid);
            if (this.fields.get("pid").length() == 9){
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("wrong pid");
            return false;
        }
        return false;
    }

    private boolean validateEcl(){
        String ecl = this.fields.get("ecl");
        System.out.println(ecl);
        if (this.eyeColor.contains(ecl)){
            return true;
        }
        return false;
    }

    private boolean validateHcl(){
        String hcl = this.fields.get("hcl");
        return hcl.matches("#[a-f0-9]{6}");
    }

    private boolean validateHgt(){
        String hgt = this.fields.get("hgt");
        try {
            String post = hgt.substring(hgt.length() -2);
            int pre = Integer.parseInt(hgt.substring(0, hgt.length() -2 ));
            System.out.println(post);
            System.out.println(pre);
            if (post.equals("cm")){
                if (pre >= 150 && pre <= 193){
                    return true;
                }
            } else if (post.equals("in")){
                if (pre >= 59 && pre <= 76){
                    return true;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException e){
            System.out.println("wrong hgt");
            return false;
        }
        return false;
    }

    private boolean validateEyr(){
        try {
            int eyr = Integer.parseInt(this.fields.get("eyr"));
            System.out.println(eyr);
            if (eyr >= 2020 && eyr <= 2030){
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("wrong eyr");
            return false;
        }
        return false;
    }

    private boolean validateIyr(){
        try {
            int iyr = Integer.parseInt(this.fields.get("iyr"));
            System.out.println(iyr);
            if (iyr >= 2010 && iyr <= 2020){
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("wrong iyr");
            return false;
        }
        return false;
    }

    private boolean validateByr(){
        try {
            int byr = Integer.parseInt(this.fields.get("byr"));
            System.out.println(byr);
            if (byr >= 1920 && byr <= 2002){
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("wrong byr");
            return false;
        }
        return false;
    }
}
