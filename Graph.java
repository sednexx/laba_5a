import java.util.*;
class Node{
    int number, lenght;
    public Node(int number, int lenght){
        this.number = number;
        this.lenght = lenght;
    }
    public int get_numb(){return number;}
    public int get_len(){return lenght;}
}

public class Graph{

    ArrayList<Node>[] graph;
    private int size_graph, max_size_graph;

    public Graph(){
        size_graph = 0;
        max_size_graph = 10;
        graph = new ArrayList[max_size_graph];
        for (int i = 0; i < max_size_graph; i++)
            graph[i] = new ArrayList<>();
    }

    public Graph(int size){
        size_graph = size;
        max_size_graph = 2*size+1;
        graph = new ArrayList[max_size_graph];
        for (int i = 0; i < max_size_graph; i++)
            graph[i] = new ArrayList<>();
    }

    public int size(){return size_graph;}

    private void func_resize(){
        int new_max_size_graph = 2*max_size_graph+1;
        
        ArrayList<Node>[] new_graph = new ArrayList[new_max_size_graph];
        for (int i = 0; i < new_max_size_graph; i++){
            new_graph[i] = new ArrayList<>();
            if(i<size_graph){
                new_graph[i] = graph[i];
            }
        }
        graph = new_graph;
        max_size_graph = new_max_size_graph;
    }

    public void push(int a, int b, int lenght){
        while(a>=max_size_graph)
            func_resize();
        if(a>=size_graph)
            size_graph = a+1;
        Node node = new Node(b, lenght);
        graph[a].add(node);
    }

    public int[][] list_to_matrix(){
        int[][] arr = new int[size()][size()];
        for(int i=0; i<size(); i++){
            for(int j=0; j<size(); j++)
                arr[i][j] = Integer.MAX_VALUE;
        }
        
        for(int i=0; i<size(); i++){
            Iterator<Node> iter = graph[i].iterator();
            while(iter.hasNext()){
                Node next_node = iter.next();
                arr[i][next_node.get_numb()] =next_node.get_len();
            }
        }

        return arr;
    }

    public void PAM(int[][] arr){
        for (int i = 0; i < size(); i++){
            for (int j = 0; j < size(); j++){
                if(arr[i][j]!=Integer.MAX_VALUE)
                    System.out.print(arr[i][j] + "\t");
                else
                    System.out.print("inf" + "\t");
            }
            System.out.println();
        }
    }

    public void print_adjacency_matrix(){
        int[][] arr = list_to_matrix();
        PAM(arr);
    }

    public void print_array_list(){
        for(int i=0; i<size(); i++){
            System.out.print("["+ (i+1) +"]:\t");
            Iterator<Node> it = graph[i].iterator();
            while (it.hasNext()){
                Node node = it.next();
                System.out.print( "->  (" + (node.number+1) + ", len=" + node.lenght + ")  ");
            }
            System.out.println();
        }
    }
}