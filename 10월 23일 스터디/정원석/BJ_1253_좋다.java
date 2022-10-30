import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1253_좋다 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt((br.readLine()));
        int[] arr = new int[N];

        int idx = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        while(st.hasMoreTokens()){
            arr[idx++] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        int good = 0;

        for(int i = 0 ; i < arr.length; i++){
            int num = arr[i];

            //투 포인터
            int start = 0;
            int end = arr.length - 1;
            int sum = 0;

            while(start < end){
                sum = arr[start] + arr[end];
                if(sum == num){
                    // 조건이 다른 두 수의 합이므로 start,end가 현재 num와 idx가 같으면 안된다.
                    if(i == start)
                        start++;
                    else if(i == end)
                        end--;
                    else{
                        good++;
                        break;
                    }
                }

                if(arr[start] + arr[end] > num)
                    end--;
                else if(arr[start] + arr[end] < num)
                    start++;
            }
        }
        System.out.println(good);

    }
}
