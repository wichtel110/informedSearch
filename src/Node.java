import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node>, Comparator<Node> {

    private Node child;
    private Node parent;
    private List<Node> childList;
    private int col = 3;
    private int heuristicValue;

    private int puzzle[];
    private boolean isVisited;

    public Node(int arr[])
    {
        this.puzzle = arr;
        this.isVisited = false;
        this.childList = new LinkedList<>();
        this.parent = this;
    }

    public void setHeuristicValue(int value){
        this.heuristicValue = value;
    }

    public int getHeuristicValue(){
        return this.heuristicValue;
    }

    //Get the pos of 0
    public int getPosZero(int arr[])
    {
        int result = 0;
        for(int i = 0; i< arr.length;i++)
        {
            if(arr[i] == 0 )
                result = i;
        }
        return result;
    }

    //All Movements UP DOWN RIGHT LEFT
    public void moveUp(int arr[], int posZero)
    {
        int tempVal;

        int tempPuzzle[] =copyArr(arr);
        if(posZero/col > 0)
        {
            tempVal = tempPuzzle[posZero-col];
            tempPuzzle[posZero] = tempVal;
            tempPuzzle[posZero-col] = 0;

            Node child = new Node(tempPuzzle);
            childList.add(child);
            child.parent = this;
        }
    }
    public void moveDown(int arr[], int posZero)
    {
        int tempVal;
        int tempPuzzle[] =copyArr(arr);

        if(posZero/col < col-1 )
        {
            tempVal = arr[posZero+col];
            tempPuzzle[posZero] = tempVal;
            tempPuzzle[posZero+col] = 0;

            Node child = new Node(tempPuzzle);
            childList.add(child);
            child.parent = this;
        }
    }
    public void moveRight(int arr[], int posZero)
    {
        int tempVal;
        int tempPuzzle[] = copyArr(arr);

        if(posZero % col < col -1){
            tempVal = arr[posZero+1];
            tempPuzzle[posZero] = tempVal;
            tempPuzzle[1+posZero] = 0;

            Node child = new Node(tempPuzzle);
            childList.add(child);
            child.parent = this;
        }

    }
    public void moveLeft(int arr[], int posZero)
    {
        int tempVal;
        int tempPuzzle[] = copyArr(arr);

        if(posZero % col > 0){
            tempVal = arr[posZero-1];
            tempPuzzle[posZero] = tempVal;
            tempPuzzle[posZero-1] = 0;

            Node child = new Node(tempPuzzle);
            childList.add(child);
            child.parent = this;
        }
    }
    public void expandMoves()
    {
        int posZero = getPosZero(this.puzzle);
        moveDown(this.puzzle,posZero);
        moveLeft(this.puzzle,posZero);
        moveRight(this.puzzle,posZero);
        moveUp(this.puzzle,posZero);

    }

    //COPY ARRAY
    public int[] copyArr(int arr[]){
        int tempArray[] = new int[9];

        for(int i = 0; i<arr.length; i++)
        {
            tempArray[i] = arr[i];
        }
        return tempArray;
    }
    //CHECK IF SAME ( Check For Goal Node)
    public boolean checkIfSame(int child[])
    {
        boolean value = true;

        for(int i = 0; i< this.puzzle.length;i++){
            if(this.puzzle[i] == child[i])
                value = true;
            else
                return false;
        }
        return value;
    }

    public List<Node> getChildList()
    {
        return childList;
    }

    public Node getParent()
    {
        return this.parent;
    }

    public int[] getPuzzle(){
        return this.puzzle;
    }

    public void printArr()
    {
        for(int i = 0; i<this.puzzle.length; i++)
        {
            if (i % col < 2)
                System.out.print(puzzle[i]);
            else
                System.out.println(puzzle[i]);
        }
        System.out.println(" ");
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public int compareTo(Node o) {
        return 0;
    }

    @Override
    public int compare(Node o1, Node o2) {
        return o1.heuristicValue - o2.heuristicValue;
    }
}
