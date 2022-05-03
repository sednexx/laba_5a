import java.util.*;

public class Graph_a {
    Graph gr;
    public Graph_a(){
        gr = new Graph();
    }

    private class Type_FS{
        int[] first, second;
        public Type_FS(){
            first = new int[gr.size()];
            second = new int[gr.size()];
        }
    }
    private class Edges {
        int start, end, lenght;
    }
    private class Kru_nodes {
        int last_n, c;
        int[] nodes;
        public Kru_nodes(){
            nodes = new int[gr.size()];
        }
    }

    private Type_FS DFS(Type_FS D, int i) {
        D.second[i] = 1;
        Iterator<Node> iter = gr.graph[i].iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if (D.second[node.get_numb()] == 0  && node.get_len()!=Integer.MAX_VALUE) {
                D.first[node.get_numb()]=i;
                D = DFS(D, node.get_numb());
            }
        }
        return D;
    }

    public void depth_first_search() {
        int[][] arr = gr.list_to_matrix();
        Type_FS rd = new Type_FS();
        for (int i = 0; i < gr.size(); i++){
            rd.second[i] = 0;
            rd.first[i] = i;
        }
    
        rd = DFS(rd, 0);
    
        Graph new_gr = new Graph();
        for (int i = 0; i < gr.size(); i++){
            if(i!=rd.first[i])
                new_gr.push(rd.first[i], i, arr[rd.first[i]][i]);
        }
        //new_gr.graph.resize(gr.size());
    
        System.out.println("depth-first search:");
        new_gr.print_array_list();
    }

    private Type_FS BFS(Type_FS D, int i) {
        Iterator<Node> iter = gr.graph[i].iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if (D.second[node.get_numb()]== 0){
                D.second[node.get_numb()] = 1;
                D.first[node.get_numb()]=i;
            }
        }
    
        iter = gr.graph[i].iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if (D.second[node.get_numb()] == 1) {
                D.second[node.get_numb()] = 2;
                D = BFS(D, node.get_numb());
            }
        }
        return D;
    }

    public void breadth_first_search() {
        int[][] arr = gr.list_to_matrix();
        Type_FS rd = new Type_FS();
        for (int i = 0; i < gr.size(); i++){
            rd.second[i] = 0;
            rd.first[i] = i;
        }
        rd.second[0] = 2;
        rd = BFS(rd, 0);
    
        Graph new_gr = new Graph();
        for (int i = 0; i < gr.size(); i++){
            if(i!=rd.first[i])
                new_gr.push(rd.first[i], i, arr[rd.first[i]][i]);
        }
    
        System.out.println("breadth-first search:");
        new_gr.print_array_list();
    }

    private Type_FS alg_D(int n, Type_FS D) {
        D.second[n] = 1;
        Iterator<Node> iter = gr.graph[n].iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if (D.first[node.get_numb()] > (D.first[n] + node.get_len()) && D.second[node.get_numb()]==0) {
                D.first[node.get_numb()] = D.first[n] + node.get_len();
            }
        }
        iter = gr.graph[n].iterator();
        while(iter.hasNext()){
            Node node = iter.next();
            if(D.second[node.get_numb()]==0)
                D = alg_D(node.get_numb(), D);
        }
        return D;
    }

    public void Dijkstra(int s) {
        s -= 1;
    
        Type_FS rd = new Type_FS();
    
        for (int i = 0; i < gr.size(); i++){
            rd.first[i] = Integer.MAX_VALUE;
            rd.second[i] = 0;
        }
        rd.first[s] = 0;
    
        rd = alg_D(s, rd);
        
        System.out.println("Algorithm Dijkstra:");
        for (int i = 0; i < gr.size(); i++){
            System.out.print((i+1) + "\t");
        }
        System.out.println();
        for (int i = 0; i < gr.size(); i++){
            System.out.print(rd.first[i] + "\t");
        }
        System.out.println();
    }

    public void Floyd_Warshall() {
        int[][] arr = gr.list_to_matrix();
        for (int k = 0; k < gr.size(); k++)
            for (int i = 0; i < gr.size(); i++)
                for (int j = 0; j < gr.size(); j++)
                    if(arr[i][k] != Integer.MAX_VALUE && arr[k][j] != Integer.MAX_VALUE && i!=j)
                        arr[i][j] = Math.min(arr[i][j], arr[i][k] + arr[k][j]);
    
        System.out.println("Algorithm Floyd-Warshall:");
        gr.PAM(arr);
    }

    private boolean orientaton_check(int[][] arr) {
        boolean ch = true;
        for (int i = 0; i < gr.size(); i++) {
            for (int j = i; j < gr.size(); j++) {
                if (arr[i][j] == arr[j][i])
                    ch = ch && true;
                else
                    ch = ch && false;
            }
        }
        return ch;
    }
    
    private ArrayList<Edges> sorting_by_length(ArrayList<Edges> mass) {
        for (int i = 1; i < mass.size(); ++i){
            for (int r = 0; r < mass.size() - i; r++) {
                if (mass.get(r).lenght > mass.get(r+1).lenght) {
                    Edges temp1 = mass.get(r);
                    Edges temp2 = mass.get(r+1);
                    mass.remove(r+1);
                    mass.remove(r);
                    mass.add(r, temp1);
                    mass.add(r+1, temp2);
                }
            }
        }
        return mass;
    }

    public void Prim_algorithm(){
        int[][] arr = gr.list_to_matrix();
        if (!orientaton_check(arr)) //граф не ориентированный
            return;
        boolean[] belongs = new boolean[gr.size()];
        ArrayList<Edges> edges = new ArrayList();
        for (int i = 0; i < gr.size(); i++) {
            belongs[i] = false;
            for (int j = i; j < gr.size(); j++) {
                if (arr[i][j] != Integer.MAX_VALUE) {
                    Edges node = new Edges();
                    node.start = i;
                    node.end = j;
                    node.lenght = arr[i][j];
                    edges.add(node);
                }
            }
        }
        edges = sorting_by_length(edges);
    
        System.out.println("Prim's algorithm:");
        belongs[edges.get(0).start] = true;
        Graph new_gr = new Graph(gr.size());
        for (int i = 0; i < edges.size(); i++){
            if(belongs[edges.get(i).start] && !belongs[edges.get(i).end] || !belongs[edges.get(i).start] && belongs[edges.get(i).end]){
                if(belongs[edges.get(i).start])
                    new_gr.push(edges.get(i).start, edges.get(i).end, edges.get(i).lenght);
                else
                    new_gr.push(edges.get(i).end, edges.get(i).start, edges.get(i).lenght);
                belongs[edges.get(i).start] = true;
                belongs[edges.get(i).end] = true;
                //System.out.println("("+ (edges.get(i).start+1) +", "+ (edges.get(i).end+1) +", len="+ edges.get(i).lenght +')');
            }
        }
        new_gr.print_array_list();
    }

    private Kru_nodes helper_function_kru(int n, Kru_nodes N){
        if (N.nodes[n]<0){
            N.last_n = n;
            N.c = N.nodes[n];
            return N;
        }
        N = helper_function_kru(N.nodes[n], N);
        N.nodes[n] = N.last_n;
        return N;
    }

    public void Kruskal_algorithm() {
        int[][] arr = gr.list_to_matrix();
        if (!orientaton_check(arr)) //граф не ориентированный
            return;
    
        Kru_nodes kru_N = new Kru_nodes();
        ArrayList<Edges> edges = new ArrayList();
        for (int i = 0; i < gr.size(); i++) {
            kru_N.nodes[i] = -1-i;
            for (int j = i; j < gr.size(); j++) {
                if (arr[i][j] != Integer.MAX_VALUE) {
                    Edges node = new Edges();
                    node.start = i;
                    node.end = j;
                    node.lenght = arr[i][j];
                    edges.add(node);
                }
            }
        }
        edges = sorting_by_length(edges);
        
        System.out.println("Kruskal's algorithm:");
        for (int i = 0; i < edges.size(); i++){
            kru_N = helper_function_kru(edges.get(i).end, kru_N);
            int c2 = kru_N.c;
            kru_N = helper_function_kru(edges.get(i).start, kru_N);
            if(kru_N.c != c2){
                kru_N.nodes[kru_N.last_n] = edges.get(i).end;
                System.out.println("("+ (edges.get(i).start+1) +", "+ (edges.get(i).end+1) +", len="+ edges.get(i).lenght +')');
            }
        }
    }
    
    public void add(int a, int b, int lenght) { gr.push(a, b, lenght); }
    public void print_AL() { gr.print_array_list(); }
    public void print_AM() { gr.print_adjacency_matrix(); }
}