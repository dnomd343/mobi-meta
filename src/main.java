import mobimeta.*;

import java.io.File;
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
        meta.setEXTHRecords();
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
        if (!flag) { // without `updated title` field
            exthList.add(new EXTHRecord(503, name, encoding));
        }
        meta.setFullName(name);
    }

    private static void setAuthor(MobiMeta meta, String author) {
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        boolean flag = false;
        for (EXTHRecord rec : exthList) {
            if (rec.getRecordType() == 100) {
                rec.setData(author, encoding);
                flag = true;
            }
        }
        if (!flag) { // without `author` field
            exthList.add(new EXTHRecord(100, author, encoding));
        }
    }

    private static void setISBN(MobiMeta meta, String isbn) {
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        boolean flag = false;
        for (EXTHRecord rec : exthList) {
            if (rec.getRecordType() == 104) {
                rec.setData(isbn, encoding);
                flag = true;
            }
        }
        if (!flag) { // without `ISBN` field
            exthList.add(new EXTHRecord(104, isbn, encoding));
        }
    }

    private static void setASIN(MobiMeta meta, String asin) {
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        boolean flag = false;
        for (EXTHRecord rec : exthList) {
            int recType = rec.getRecordType();
            if (recType == 113 || recType == 504) {
                rec.setData(asin, encoding);
                flag = true;
            }
        }
        if (!flag) { // without `ASIN` field
            exthList.add(new EXTHRecord(113, asin, encoding));
            exthList.add(new EXTHRecord(504, asin, encoding)); // `504` priority on kindle
        }
    }

    private static void setCDE(MobiMeta meta, String cde) {
        String encoding = meta.getCharacterEncoding();
        List<EXTHRecord> exthList = meta.getEXTHRecords();
        boolean flag = false;
        for (EXTHRecord rec : exthList) {
            if (rec.getRecordType() == 501) {
                rec.setData(cde, encoding);
                flag = true;
            }
        }
        if (!flag) { // without `CDE type` field
            exthList.add(new EXTHRecord(501, cde, encoding));
        }
    }

    public static void showHelp() {
        String prefix = "java -jar mobi-meta.jar";
        System.out.println();
        System.out.println("Display and edit metadata of MOBI or AZW3 file.");
        System.out.println("  (https://github.com/dnomd343/mobi-meta.git)");
        System.out.println();
        System.out.println("Usage:");
        System.out.printf("    %s show INPUT\n", prefix);
        System.out.printf("    %s edit INPUT OUTPUT [OPTIONS]\n", prefix);
        System.out.println();
        System.out.println("Options:");
        System.out.println("    --name NAME        Set eBook name");
        System.out.println("    --author AUTHOR    Set eBook author");
        System.out.println("    --isbn ISBN        Set eBook ISBN");
        System.out.println("    --asin ASIN        Set eBook ASIN");
        System.out.println("    --pdoc             Mark CDE-type as PDOC");
        System.out.println("    --ebok             Mark CDE-type as EBOK");
        System.out.println("    --ebsp             Mark CDE-type as EBSP");
        System.out.println();
        System.out.println("Examples:");
        System.out.printf("  %s show my_book.mobi\n", prefix);
        System.out.printf("  %s edit origin.mobi new.mobi --name NEW_NAME\n", prefix);
        System.out.printf("  %s edit origin.mobi new.mobi --asin ASIN --ebok\n", prefix);
        System.out.println();
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            showHelp();
        }
        if (args[0].equalsIgnoreCase("show")) {
            if (args.length != 2) {
                showHelp();
            }
            showMeta(new File(args[1]));
        } else if (args[0].equalsIgnoreCase("edit")) {
            if (args.length < 3) {
                showHelp();
            }
            String name = null;
            String author = null;
            String isbn = null;
            String asin = null;
            String cde = null;
            for (int i = 3; i < args.length; ++i) {
                if (args[i].equals("--pdoc")) {
                    cde = "PDOC";
                } else if (args[i].equals("--ebok")) {
                    cde = "EBOK";
                } else if (args[i].equals("--ebsp")) {
                    cde = "EBSP";
                } else if (args[i].equals("--name") && i + 1 != args.length) {
                    name = args[++i];
                } else if (args[i].equals("--author") && i + 1 != args.length) {
                    author = args[++i];
                } else if (args[i].equals("--isbn") && i + 1 != args.length) {
                    isbn = args[++i];
                } else if (args[i].equals("--asin") && i + 1 != args.length) {
                    asin = args[++i];
                } else {
                    showHelp();
                }
            }
            System.out.println(splitLine);
            MobiMeta meta = readFile(new File(args[1])); // input file
            if (name != null) {
                System.out.printf("Name: %s\n", name);
                setBookName(meta, name);
            }
            if (author != null) {
                System.out.printf("Author: %s\n", author);
                setAuthor(meta, author);
            }
            if (isbn != null) {
                System.out.printf("ISBN: %s\n", isbn);
                setISBN(meta, isbn);
            }
            if (asin != null) {
                System.out.printf("ASIN: %s\n", asin);
                setASIN(meta, asin);
            }
            if (cde != null) {
                System.out.printf("CDE-type: %s\n", cde);
                setCDE(meta, cde);
            }
            saveFile(meta, new File(args[2])); // output file
            System.out.println(splitLine);
            System.out.printf("File save as: %s\n", args[2]);
            System.out.println(splitLine);
        } else {
            showHelp();
        }
    }
}
