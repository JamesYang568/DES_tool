package Entity;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.ArrayList;

/**
 * 文件操作类，进行文件的读取和精细化读写，静态方法
 */
public class MS_Word_Wrapper {
    /**
     * 读取doc文件，传入要读文件的路径
     */
    public static String[] read_doc(String file_path) throws IOException {
        FileInputStream in = new FileInputStream(file_path);
        WordExtractor extractor = new WordExtractor(in);
        String text = extractor.getText();
        in.close();
        return text.split("\r\n");
    }

    /**
     * 读取doc文件，传入要读文件的路径
     */
    public static String[] read_docx(String file_path) throws IOException {
        InputStream in = new FileInputStream(file_path);
        XWPFDocument document = new XWPFDocument(in);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String text = extractor.getText();     //得到的多行数据需要进行多行处理，结尾以/n作为回车
        in.close();
        return text.split("\n");
    }

    /**
     * 写入docx文件,无论是doc还时docx，都需要在生成时变为docx
     * 传入文件路径和内容，如果内容为空则不生成文件
     */
    public static void write_docx_file(String file_path, String[] content) throws IOException {
        if (content.length == 0)
            return;
        XWPFDocument document = new XWPFDocument();  //统一使用XWPF来写文件
        XWPFParagraph p = document.createParagraph();  //必须要创建一个段落才能往doc对象中写东西
        XWPFRun r = p.createRun();     //创建一个段落的文本信息
        // 当创建一个段落的时候，XWPFDocument对象将这个段落放在了自己对应的ArrayList里面，同理
        for (int i = 0; i < content.length - 1; i++) {
            r.setText(content[i]);
            r.addCarriageReturn();
        }
        r.setText(content[content.length - 1]); //最后一行
        FileOutputStream out = new FileOutputStream(file_path);
        document.write(out);    //需要借助通道才能在硬盘上写下东西，注意之前的p是绑定在doc中的，r是绑定在p中的。
        out.close();
    }

    /**
     * 读取txt文件，传入要读文件的路径地址  txt文件是不会读取回车\n的，所以可以放心使用
     */
    public static String[] read_txt(String file_path) throws IOException {
        ArrayList<String> text = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(file_path));
        String tmp;
        while ((tmp = in.readLine()) != null) {
            text.add(tmp);
        }
        in.close();
        return text.toArray(new String[0]);
    }

    /**
     * 写入txt文件
     * 传入文件路径和内容，如果内容为空则不生成文件
     */
    public static void write_txt_file(String file_path, String[] content) throws IOException {
        if (content.length == 0)
            return;
        BufferedWriter out = new BufferedWriter(new FileWriter(file_path));
        for (String s : content) {
            out.write(s);
            out.newLine();
        }
        out.close();
    }
}
