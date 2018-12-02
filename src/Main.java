import javax.sound.midi.SysexMessage;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        int arr[] = {2,8,3,1,6,4,7,0,5};

        int goalArr[] = {1,2,3,8,0,4,7,6,5};
        boolean goalFound = false;
        int counter = 0;
        Node head = new Node(arr);
        head.setHeuristicValue(1337);
        Comparator<Node> cn = Comparator.comparingInt(Node::getHeuristicValue);

        PriorityQueue<Node> pq = new PriorityQueue<>(cn);
        pq.add(head);

        while(!pq.isEmpty() && !goalFound){
            Node currentNode = pq.remove();
            currentNode.expandMoves();
            counter++;

            for(int i = 0; i< currentNode.getChildList().size(); i++){
                Node currentChild = currentNode.getChildList().get(i);
                calcSecondHeuristicValue(currentChild,goalArr);

                if(!checkNodeForGoal(currentChild,goalArr) && !checkParentNode(currentChild,currentChild.getParent())){
                    pq.add(currentChild);
                }else{
                    goalFound = true;
                    currentChild.printArr();
                    System.out.println("Counter: " + counter);
                }
            }
        }
    }

    public static boolean checkParentNode(Node currentChild, Node parent) {
        boolean isSame = false;
        int current[] = currentChild.getPuzzle();

        for(int i = 0; i < current.length; i++){
            if(current[i] != parent.getPuzzle()[i]){
                return false;
            }else {
                isSame = true;
            }
        }
        return isSame;
    }

    private static boolean checkNodeForGoal(Node currentChild, int[] goalArr) {
        boolean goalFound = false;

        for(int i = 0; i < currentChild.getPuzzle().length; i++){
            if(currentChild.getPuzzle()[i] != goalArr[i]){
                return false;
            }else{
                goalFound = true;
            }
        }

        return goalFound;
    }


    public static void calcHeuristicValue(Node node, int goalArr[]) {
        int puzzle[] = node.getPuzzle();
        int counter = 0;
        for(int i = 0; i< puzzle.length; i++){
           if(puzzle[i] != goalArr[i]){
               counter += 1;
           }
        }
        node.setHeuristicValue(counter);
    }

    public static void calcSecondHeuristicValue(Node node, int goalArr[]){
        int puzzle[] = node.getPuzzle();
        int counter = 0,tempVal = 0;
        for(int i = 0; i< puzzle.length; i++){
            for(int f = 0; f< goalArr.length; f++){
                if(puzzle[i] == goalArr[f]){
                    tempVal = i-f;
                    if(tempVal < 0){
                        tempVal *= -1;
                    }
                }
            }
            counter += tempVal;
        }
        node.setHeuristicValue(counter);
    }
}
