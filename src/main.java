import mobimeta.*;

import java.io.File;
import java.util.List;

class Main {
    static String splitLine = "================================================================";

    public static void showMeta(File file) {
        System.out.println(splitLine);
        System.out.println("File name: " + file.getName());
        MobiMeta meta;
        try {
            meta = new MobiMeta(file);
        } catch (MobiMetaException err) {
            System.err.println("Error: " + err.getMessage());
            return;
        }
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        System.out.println("Book name: " + meta.getFullName());
        System.out.println("Encoding: " + encoding);
        System.out.println("Locale: " + meta.getLocale());
        System.out.println(splitLine);
        System.out.print(meta.getMetaInfo());

        System.out.print(splitLine);
        for (EXTHRecord rec : exthList) {
            if (!rec.isKnownType()) {
                continue;  // skip unknown EXTH record
            }
            System.out.println();
            int type = rec.getRecordType();
            String desc = rec.getTypeDescription();
            String content = StreamUtils.byteArrayToString(rec.getData(), encoding);
            System.out.println("[" + type + "](" + desc + "): " + content);
        }

        System.out.println(splitLine);
        for (EXTHRecord rec : exthList) {
            if (rec.isKnownType()) {
                continue;  // only show unknown type
            }
            int type = rec.getRecordType();
            String content = StreamUtils.byteArrayToString(rec.getData(), encoding);
            System.out.println("[" + type + "]: " + content);
        }
        System.out.println(splitLine);
    }

    public static void main(String[] args) {

        String testFile = "XXRS.azw3";

        File target = new File(testFile);

        showMeta(target);

    }
}
