package com.gtis.portal.properties;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;

public class SafeProperties extends Properties {
    private static final long serialVersionUID = 5011694856722313621L;

    private static final String keyValueSeparators = "=: \t\r\n\f";

    private static final String strictKeyValueSeparators = "=:";

    private static final String whiteSpaceChars = " \t\r\n\f";

    private PropertiesContext context = new PropertiesContext();
    private List<PropertyEntry> proList = new ArrayList<PropertyEntry>();
    private LinkedHashMap<String,PropertyEntry> entryMap = new LinkedHashMap<String, PropertyEntry>();

    public static void main(String[] args) throws Exception{
        /*try {
            FileInputStream input = new FileInputStream("d:\\gis.properties");
            SafeProperties safeProp = new SafeProperties();
            safeProp.load(input);
            input.close();
            safeProp.addComment("New Comment");
            safeProp.put("New-Key", "New====Value");
            FileOutputStream output = new FileOutputStream("d:\\gis.properties");
            safeProp.store(output, null);
            output.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }*/

        /*try {
            SafeProperties prop = new SafeProperties();
            String filePath = "d:\\gis.properties";
            File file = new File(filePath);
            if (file.exists()){
                FileInputStream inputStream = new FileInputStream(file);
                prop.load(inputStream);
                prop.initConfigCommentList();
                PropertiesContext context = prop.getContext();
                List commentOrEntrys = context.getCommentOrEntrys();
                if (commentOrEntrys != null && commentOrEntrys.size() > 0){
                    for (int i = 0; i < commentOrEntrys.size(); i++) {
                        System.out.println(commentOrEntrys.get(i));
                    }
                }

                List<PropertyEntry> proList = prop.getProList();
                if (proList != null && proList.size() > 0){
                    for (int i = 0; i < proList.size(); i++) {
                        System.out.println(proList.get(i));
                    }
                }
                System.out.println(JSON.toJSONString(proList));

                //此处的操作时编辑备注信息，在原有备注上增加一行空行，在备注后增加新的备注，之后删除原有备注
                //该操作只针对有备注信息的，如果原来没有备注信息，需要对原有数据增加备注
                *//*PropertyEntry entry = proList.get(8);
                entry.setComment("#测试1111一下这个注释编辑\r\n#测试一2222下这个配置编辑");
                int length = entry.getLineNum();
                if (length > 0){
                    prop.getContext().getCommentOrEntrys().add(entry.getLastIndex(),"");
                    prop.getContext().getCommentOrEntrys().add(entry.getIndex()+1,entry.getComment());
                }else{
                    if (StringUtils.isNotBlank(entry.getComment())){
                        prop.getContext().getCommentOrEntrys().add(entry.getLastIndex(),"");
                        prop.getContext().getCommentOrEntrys().add(entry.getIndex()+1,entry.getComment());
                    }
                }
                for (int i = 0; i < length; i++) {
                    prop.getContext().getCommentOrEntrys().remove(entry.getLastIndex()+1);
                }
                prop.put(entry.getKey(), entry.getValue()+"test测试sss一下");
                FileOutputStream output = new FileOutputStream(filePath);
                prop.store(output, null);
                output.close();*//*
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }*/
    }

    public PropertiesContext getContext() {
        return context;
    }

    public List<PropertyEntry> getProList() {
        return proList;
    }

    public LinkedHashMap<String,PropertyEntry> getEntryMap(){
        return entryMap;
    }

    public synchronized void load(InputStream inStream) throws IOException {

        BufferedReader in;

        in = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
        while (true) {
            // Get next line
            String line = in.readLine();
            // intract property/comment string
            if (line == null)
                return;

            line = line.replace("\uFEFF","");
            String intactLine = line;
            if (line.length() > 0) {

                // Find start of key
                int len = line.length();
                int keyStart;
                for (keyStart = 0; keyStart < len; keyStart++)
                    if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
                        break;

                // Blank lines are ignored
                if (keyStart == len)
                    continue;

                // Continue lines that end in slashes if they are not comments
                char firstChar = line.charAt(keyStart);

                if ((firstChar != '#') && (firstChar != '!')) {
                    while (continueLine(line)) {
                        String nextLine = in.readLine();
                        intactLine = intactLine + "\n" + nextLine;
                        if (nextLine == null)
                            nextLine = "";
                        String loppedLine = line.substring(0, len - 1);
                        // Advance beyond whitespace on new line
                        int startIndex;
                        for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
                            if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1)
                                break;
                        nextLine = nextLine.substring(startIndex, nextLine.length());
                        line = new String(loppedLine + nextLine);
                        len = line.length();
                    }

                    // Find separation between key and value
                    int separatorIndex;
                    for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
                        char currentChar = line.charAt(separatorIndex);
                        if (currentChar == '\\')
                            separatorIndex++;
                        else if (keyValueSeparators.indexOf(currentChar) != -1)
                            break;
                    }

                    // Skip over whitespace after key if any
                    int valueIndex;
                    for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
                        if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                            break;

                    // Skip over one non whitespace key value separators if any
                    if (valueIndex < len)
                        if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1)
                            valueIndex++;

                    // Skip over white space after other separators if any
                    while (valueIndex < len) {
                        if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
                            break;
                        valueIndex++;
                    }
                    String key = line.substring(keyStart, separatorIndex);
                    String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

                    // Convert then store key and value
                    key = loadConvert(key);
                    value = loadConvert(value);
                    //memorize the property also with the whold string
                    put(key, value, intactLine);
                } else {
                    //memorize the comment string
                    context.addCommentLine(intactLine);
                }
            } else {
                //memorize the string even the string is empty
                context.addCommentLine(intactLine);
            }
        }
    }

    /*
     * Converts encoded &#92;uxxxx to unicode chars and changes special saved
     * chars to their original forms
     */
    private String loadConvert(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        outBuffer.append('\t'); /* ibm@7211 */

                    else if (aChar == 'r')
                        outBuffer.append('\r'); /* ibm@7211 */
                    else if (aChar == 'n') {
                  /*
                   * ibm@8897 do not convert a \n to a line.separator
                   * because on some platforms line.separator is a String
                   * of "\r\n". When a Properties class is saved as a file
                   * (store()) and then restored (load()) the restored
                   * input MUST be the same as the output (so that
                   * Properties.equals() works).
                   *
                   */
                        outBuffer.append('\n'); /* ibm@8897 ibm@7211 */
                    } else if (aChar == 'f')
                        outBuffer.append('\f'); /* ibm@7211 */
                    else
                  /* ibm@7211 */
                        outBuffer.append(aChar); /* ibm@7211 */
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    public synchronized void store(OutputStream out, String header) throws IOException {
        BufferedWriter awriter;
        awriter = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
        if (header != null)
            writeln(awriter, "#" + header);
        List entrys = context.getCommentOrEntrys();
        for (Iterator iter = entrys.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            if (obj.toString() != null) {
                writeln(awriter, obj.toString());
            }
        }
        awriter.flush();
    }

    private static void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    private boolean continueLine(String line) {
        int slashCount = 0;
        int index = line.length() - 1;
        while ((index >= 0) && (line.charAt(index--) == '\\'))
            slashCount++;
        return (slashCount % 2 == 1);
    }


    public synchronized Object put(Object key, Object value) {
        context.putOrUpdate(key.toString(), value.toString());
        return super.put(key, value);
    }

    public synchronized Object put(Object key, Object value, String line) {
        context.putOrUpdate(key.toString(), value.toString(), line);
        return super.put(key, value);
    }


    public synchronized Object remove(Object key) {
        context.remove(key.toString());
        return super.remove(key);
    }

    public synchronized void initConfigCommentList(){
        List commentOrEntrys = context.getCommentOrEntrys();
        StringBuffer strBuf = new StringBuffer();
        int lineNum = 0;
        for (int i = 0; i < context.getCommentOrEntrys().size(); i++) {
            String line = commentOrEntrys.get(i).toString();
            //针对非空行进行数据处理
            if (StringUtils.isNotBlank(line)){
                //如果语句是以#开始，则表示是注释行，接着向下执行
                //继续执行时，需要把这些注释信息都记录下来，为了写到实体对象的comment属性中
                if (StringUtils.startsWith(line,"#")){
                    strBuf.append(line+"\n");
                    lineNum ++;
                    continue;
                }else {
                    if (commentOrEntrys.get(i) instanceof PropertyEntry){
                        PropertyEntry propertyEntry = (PropertyEntry)commentOrEntrys.get(i);
                        propertyEntry.setComment(strBuf.toString());
                        propertyEntry.setIndex(i);
                        propertyEntry.setLineNum(lineNum);
                        propertyEntry.setLastIndex(i-lineNum);
                        commentOrEntrys.set(i,propertyEntry);
                        proList.add(propertyEntry);
                        entryMap.put(propertyEntry.getKey(),propertyEntry);
                        strBuf = new StringBuffer();

                        lineNum = 0;
                    }
                }
            }else {
                lineNum ++;
            }
        }
    }

    /**
     * @param comment
     */
    public void addComment(String comment) {
        if (comment != null) {
            context.addCommentLine("#" + comment);
        }
    }

}