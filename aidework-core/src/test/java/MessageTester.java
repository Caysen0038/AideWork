import java.io.UnsupportedEncodingException;
import java.util.*;

public class MessageTester {
    private static String key="sdqwnei";
    public static void main(String[] args){
        String content="哈哈,.'哈[[&啊aaaaaa";
        content="'[]\\_^`‘'";
        //System.out.println(MessageHelper.BEA(content,key));
        //System.out.println(MessageHelper.DBEA(MessageHelper.BEA(content,key),key));
    }
    private static Map<Integer, String> map;// 获得码表
    // 字节接收变量，接收哈夫曼编码连接够8位时转换的字节
    private static int exmpCode;
    public static int size_File;

    private static void init() {
        map= createMap("hello world");
        String filePath = "F:\\JAVA\\压缩后.txt";
//        for(Integer key:map.keySet()){
//            map.put(key,changeStringToByte(map.get(key)));
//        }
        System.out.println(map);
    }


    private static String changeStringToByte(String str) {
        if (8 <= str.length()) {
            exmpCode = ((str.charAt(0) - 48) * 128
                    + (str.charAt(1) - 48) * 64 + (str.charAt(2) - 48) * 32
                    + (str.charAt(3) - 48) * 16 + (str.charAt(4) - 48) * 8
                    + (str.charAt(5) - 48) * 4 + (str.charAt(6) - 48) * 2
                    + (str.charAt(7) - 48));
            str = str.substring(8);
            return str;
        }
        return "";
    }




    private static int[] getContentArray(String content) {
        /******储存字节数据的数组--索引值代表字节类型-存储值代表权值******/
        int[] array = new int[256];
        byte[] data= new byte[0];
        try {
            data = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.length; i++) {
            int b = data[i];
            array[b] ++;
        }
        return array;
    }
    /**
     * 第二步：
     * 根据统计的字节数组创建优先队列
     * @param array 统计文件字节的数组
     * @return    优先队列
     */
    private static PriorityQueue<NodeData> createQueue(int[] array){
        //定义优先队列，根据数据的权值排序从小到大
        PriorityQueue<NodeData> queue =
                new PriorityQueue<NodeData>(array.length,new Comparator<NodeData>(){
                    public int compare(NodeData o1, NodeData o2) {
                        return o1.weight - o2.weight;
                    }
                });

        for(int i=0; i<array.length; i++){
            if(0 != array[i]){
                NodeData node = new NodeData();
                node.data = i;//设置该节点的数据
                node.weight = array[i];//设置该节点的权值
                queue.add(node);
            }
        }
        return queue;
    }
    /**
     * 第三步：
     * 根据优先队列创建哈夫曼树
     * @param queue  优先队列
     * @return     哈夫曼树根节点
     */
    private static NodeData createTree(PriorityQueue<NodeData> queue){
        while(queue.size() > 1){
            NodeData left = queue.poll();//取得左节点
            NodeData right = queue.poll();//取得右节点

            NodeData root = new NodeData();//创建新节点
            root.weight = left.weight + right.weight;
            root.left=left;
            root.right=right;
            left.parent=root;
            right.parent=root;

            left.point = 0;
            right.point = 1;

            queue.add(root);
        }
        NodeData firstNode = queue.poll();
        return firstNode;
    }
    /**
     * 第四步：
     * 寻找叶节点并获得哈夫曼编码
     * @param root  根节点
     */
    private static void achievehfmCode(NodeData root){

        if(null == root.left && null == root.right){
            array_Str[root.data] = achieveLeafCode(root);
        }
        if(null != root.left){
            achievehfmCode(root.left);
        }
        if(null != root.right){
            achievehfmCode(root.right);
        }
    }
    /**
     * 根据某叶节点获得该叶节点的哈夫曼编码
     * @param leafNode  叶节点对象
     */
    private static String achieveLeafCode(NodeData leafNode){
        String str = "";
        /*****************存储节点 01 编码的队列****************/
        List<Integer> list_hfmCode = new ArrayList<>();
        list_hfmCode.add(leafNode.point);
        NodeData parent = leafNode.parent;

        while(null != parent){
            list_hfmCode.add(parent.point);
            parent = parent.parent;
        }

        int size = list_hfmCode.size();
        for(int i=size-2; i>=0; i--){
            str += list_hfmCode.get(i);
        }
        System.out.println(leafNode.data+"的哈夫曼编码为>>>"+str);
        return str;
    }
    /**
     * 第五步：
     * 根据获得的哈夫曼表创建 码表
     * Integer 为字节byte [0~256)
     * String 为哈夫曼编码
     * @return 码表
     */
    public static Map<Integer,String> createMap(String content){
        int[] hfm_Codes = getContentArray(content);
        PriorityQueue<NodeData> queue = createQueue(hfm_Codes);
        /*
         * 获得哈夫曼树的根节点，
         * this.createTree(queue)方法调用之后(含有poll())，queue.size()=0
         */
        NodeData root = createTree(queue);
        achievehfmCode(root);//获得哈夫曼编码并将其存入数组中

        Map<Integer,String> map = new HashMap<>();
        for(int i=0; i<256; i++){
            if(null != array_Str[i]){
                map.put(i, array_Str[i]);
            }
        }
        return map;
    }
    /**
     * 存储字节哈夫曼编码的数组
     * 下标：字节数据
     * 元素：哈夫曼编码
     */
    public static String[] array_Str = new String[256];
}
class NodeData{
    public int data;//节点数据
    public int weight;//该节点的权值
    public int point;//该节点所在的左右位置 0-左 1-右
    public NodeData parent;
    public NodeData left;
    public NodeData right;
}