# MOBI METADATA

> Display and edit metadata of MOBI or AZW3 file.

This project is a fork of [Java-Mobi-Metadata-Editor](https://github.com/kwkoo/Java-Mobi-Metadata-Editor), which removes the GUI, uses a simple command line to modify eBook metadata, and allows more detailed data to be displayed.

## Usage

```
Usage:
    java -jar mobi-meta.jar show INPUT
    java -jar mobi-meta.jar edit INPUT OUTPUT [OPTIONS]

Options:
    --name NAME        Set eBook name
    --author AUTHOR    Set eBook author
    --isbn ISBN        Set eBook ISBN
    --asin ASIN        Set eBook ASIN
    --pdoc             Mark CDE-type as PDOC
    --ebok             Mark CDE-type as EBOK
    --ebsp             Mark CDE-type as EBSP

Examples:
  java -jar mobi-meta.jar show my_book.mobi
  java -jar mobi-meta.jar edit origin.mobi new.mobi --name NEW_NAME
  java -jar mobi-meta.jar edit origin.mobi new.mobi --asin ASIN --ebok
```

## Example

**Read and display metadata**

```bash
$ java -jar mobi-meta.jar show 栩栩若生.azw3
================================================================
File name: 栩栩若生.azw3
Book name: 栩栩若生
Encoding: UTF-8
Locale: 4
================================================================
PDB Header
----------
Name: Xu_Xu_Ruo_Sheng
Version: 0
Creation Date: 1679122972
Modification Date: 1679122972
Last Backup Date: 0
Modification Number: 0
App Info ID: 0
Sort Info ID: 0
Type: 1112493899
Creator: 1297039945
Unique ID Seed: 4687

PalmDOC Header
--------------
Compression: PalmDOC
Text Length: 9522446
Record Count: 2325
Record Size: 4096
Encryption Type: None

MOBI Header
-----------
Header Length: 264
Mobi Type: Mobipocket Book
Unique ID: 2910076949
File Version: 8
Orthographic Index: 4294967295
Inflection Index: 4294967295
Index Names: 4294967295
Index Keys: 4294967295
Extra Index 0: 4294967295
Extra Index 1: 4294967295
Extra Index 2: 4294967295
Extra Index 3: 4294967295
Extra Index 4: 4294967295
Extra Index 5: 4294967295
First Non-Book Index: 2327
Full Name Offset: 1240
Full Name Length: 12
Min Version: 8
Huffman Record Offset: 0
Huffman Record Count: 0
Huffman Table Offset: 0
Huffman Table Length: 0
================================================================
[503](updated title): 栩栩若生

[100](author): 小叙

[108](contributor): https://github.com/dnomd343/xxrs-crawler

[103](description): <div><p>算命先生说是我天生贵命，掌花娘娘转世，有点石成金，统领花精树灵之力，待到长大成人，必可家门荣兴。</p><p>偏偏十二岁那年我得了场怪病，高烧不退，总看到骇人的景象……梦里我遇到个婆婆，她说找手眼通天的高人可为我保命。</p><p>小米收魂，起坛布阵，仙人讨封……从此我踏上征程，拜师父，研道术，求就是一个生。</p><p>……千磨万击还坚劲，任尔东南西北风。</p></div>

[101](publisher): Dnomd343

[113](ASIN): e83f0f68-7c4d-4358-84bc-2bde0775aa2f

[112](source): calibre:e83f0f68-7c4d-4358-84bc-2bde0775aa2f

[501](CDE type): EBOK

[106](publishing date): 2023-03-18T06:48:10.899110+00:00
================================================================
[524]: zh
[204]:
[205]:
[206]:
[207]:
[535]: 0730-890adc2
[201]:
[203]:
[202]:
[129]: kindle:embed:0001
[125]:
[131]:
[528]: true
================================================================
```

**Modify eBook metadata**

```bash
$ java -jar mobi-meta.jar edit 栩栩若生.azw3 test.azw3 --pdoc --name 书名 --author 佚名 --isbn 我是ISBN --asin 我是ASIN
================================================================
Name: 书名
Author: 佚名
ISBN: 我是ISBN
ASIN: 我是ASIN
CDE-type: PDOC
================================================================
File save as: test.azw3
================================================================
```

```bash
$ java -jar mobi-meta.jar show test.azw3
================================================================
File name: test.azw3
Book name: 书名
Encoding: UTF-8
Locale: 4
================================================================
PDB Header
----------
Name: Xu_Xu_Ruo_Sheng
Version: 0
Creation Date: 1679122972
Modification Date: 1679122972
Last Backup Date: 0
Modification Number: 0
App Info ID: 0
Sort Info ID: 0
Type: 1112493899
Creator: 1297039945
Unique ID Seed: 4687

PalmDOC Header
--------------
Compression: PalmDOC
Text Length: 9522446
Record Count: 2325
Record Size: 4096
Encryption Type: None

MOBI Header
-----------
Header Length: 264
Mobi Type: Mobipocket Book
Unique ID: 2910076949
File Version: 8
Orthographic Index: 4294967295
Inflection Index: 4294967295
Index Names: 4294967295
Index Keys: 4294967295
Extra Index 0: 4294967295
Extra Index 1: 4294967295
Extra Index 2: 4294967295
Extra Index 3: 4294967295
Extra Index 4: 4294967295
Extra Index 5: 4294967295
First Non-Book Index: 2327
Full Name Offset: 1224
Full Name Length: 6
Min Version: 8
Huffman Record Offset: 0
Huffman Record Count: 0
Huffman Table Offset: 0
Huffman Table Length: 0
================================================================
[503](updated title): 书名

[100](author): 佚名

[108](contributor): https://github.com/dnomd343/xxrs-crawler

[103](description): <div><p>算命先生说是我天生贵命，掌花娘娘转世，有点石成金，统领花精树灵之力，待到长大成人，必可家门荣兴。</p><p>偏偏十二岁那年我得了场怪病，高烧不退，总看到骇人的景象……梦里我遇到个婆婆，她说找手眼通天的高人可为我保命。</p><p>小米收魂，起坛布阵，仙人讨封……从此我踏上征程，拜师父，研道术，求就是一个生。</p><p>……千磨万击还坚劲，任尔东南西北风。</p></div>

[101](publisher): Dnomd343

[113](ASIN): 我是ASIN

[112](source): calibre:e83f0f68-7c4d-4358-84bc-2bde0775aa2f

[501](CDE type): PDOC

[106](publishing date): 2023-03-18T06:48:10.899110+00:00

[104](ISBN): 我是ISBN
================================================================
[524]: zh
[204]:
[205]:
[206]:
[207]:
[535]: 0730-890adc2
[201]:
[203]:
[202]:
[129]: kindle:embed:0001
[125]:
[131]:
[528]: true
================================================================
```

## License

Use the same MIT license as [Java-Mobi-Metadata-Editor](https://github.com/kwkoo/Java-Mobi-Metadata-Editor).
