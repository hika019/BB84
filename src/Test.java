public class Test {
    public void start(){
        BraKetVector a = new BraKetVector();
        BraKetVector b = new BraKetVector();
        for(int i=0; i<4; i++){

            System.out.println(a.isEqual('0'));
            System.out.println(a.isEqual('1'));
            System.out.println(a.isEqual('+'));
            System.out.println(a.isEqual('-'));
            

            System.out.println();
        }

    }

    public void exit(){
        System.exit(0);
    }
}
