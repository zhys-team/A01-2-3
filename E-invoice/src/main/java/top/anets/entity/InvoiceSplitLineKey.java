package top.anets.entity;

public class InvoiceSplitLineKey {
    private String docNum;

    private String docLine;

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum == null ? null : docNum.trim();
    }

    public String getDocLine() {
        return docLine;
    }

    public void setDocLine(String docLine) {
        this.docLine = docLine == null ? null : docLine.trim();
    }
}