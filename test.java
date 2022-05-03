import java.util.Random;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Graph_a graph_a = new Graph_a();

        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of graph vertices: ");
        int number = sc.nextInt();
        while(number <= 1 ){number = sc.nextInt();}
        for(int i=0; i<number; i++){
            for (var j = i; j < number; j++) {
                if(i!=j && (number<4 || random.nextInt(2)!=0)){
                    int buf = 1 + random.nextInt(number);
                    graph_a.add(i, j, buf);
                    graph_a.add(j, i, buf);
                }
            }
        }

        graph_a.print_AL();             System.out.println();
        graph_a.print_AM();             System.out.println();

        graph_a.depth_first_search();   System.out.println();
        graph_a.breadth_first_search(); System.out.println();

        System.out.print("Enter a vertex number to start Dijkstra's algorithm: ");
        int num = sc.nextInt();
        while(num>number || num<1 ){num = sc.nextInt();}
        graph_a.Dijkstra(num);          System.out.println();

        graph_a.Floyd_Warshall();       System.out.println();
        graph_a.Prim_algorithm();       System.out.println();
        graph_a.Kruskal_algorithm();
    }
}