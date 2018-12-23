package com.example.shergalgoizpc.xmldemo;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Demo extends Activity implements View.OnClickListener{
    TextView tv1;
    Button btnSearch;
    TextView txtSalary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        tv1=(TextView)findViewById(R.id.textView1);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        txtSalary = (TextView) findViewById(R.id.txtSalary);
        txtSalary.setText("0");

        filterBySalary();
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSearch) {
            filterBySalary();
        }
    }

    private void filterBySalary() {
        tv1.setText("");
        Long inputSalary = Long.parseLong(txtSalary.getText().toString());
        try {
            InputStream is = getAssets().open("file.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("employee");

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    long curSalary = Long.parseLong(getValue("luong", element2));
                    if (inputSalary > curSalary) continue;

                    tv1.setText(tv1.getText()+"\nTen : " + getValue("Ten", element2)+"\n");
                    tv1.setText(tv1.getText()+"ViTri : " + getValue("ViTri", element2)+"\n");
                    tv1.setText(tv1.getText()+"luong : " + getValue("luong", element2)+"\n");
                    tv1.setText(tv1.getText()+"---------------------------------");
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }
}