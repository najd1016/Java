public class Main {

    public static void main(String[] args){

        NewsDecoder newsDecoder = new NewsDecoder();

        while(true) {
            try {
                newsDecoder.fetchNewsHeadlines("https://newsapi.org/v1/articles?source=SRCHERE&sortBy=top&apiKey=ed04173c40f341aaa56eedc2898a7523");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
