import mobimeta.*;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class Main {
    static String splitLine = "================================================================";

    private static MobiMeta readFile(File file) {
        MobiMeta meta = null;
        try {
            meta = new MobiMeta(file);
        } catch (MobiMetaException err) {
            System.err.println("Error reading file: " + err.getMessage());
            System.exit(1);
        }
        return meta;
    }

    private static void saveFile(MobiMeta meta, File file) {
        try {
            meta.saveToNewFile(file);
        } catch (MobiMetaException err) {
            System.err.println("Error saving file: " + err.getMessage());
        }
    }

    private static void showMeta(File file) {
        System.out.println(splitLine);
        System.out.println("File name: " + file.getName());
        MobiMeta meta = readFile(file);
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

    private static void setBookName(MobiMeta meta, String name) {
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        boolean flag = false;
        for (EXTHRecord rec : exthList) {
            if (rec.getRecordType() == 503) {
                rec.setData(name, encoding);
                flag = true;
            }
        }
        if (!flag) { // without updated title
            exthList.add(new EXTHRecord(503, name, encoding));
        }
        meta.setFullName(name);
        meta.setEXTHRecords();
    }

    public static void main(String[] args) {

        String testFile = "XXRS.azw3";

        File target = new File(testFile);
        showMeta(target);

//        MobiMeta meta = readFile(target);
//        setBookName(meta, "生若栩栩");
//        showMeta(target);

//        saveFile(meta, new File("XXRS_new.azw3"));

    }
}
