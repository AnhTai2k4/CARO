 public boolean checkwin(int i,int j){
        int cnt=0,cnt1=0,cnt2=0;
        //5 hang ngang
        String s= b[i][j].getText();
        while(i<=col&&b[i][j].getText()==s){
            cnt1++;
            i=i+1;
        }
        i=i-cnt1;
        while(i>=2&&b[i-1][j].getText()==s){
            cnt2++;
            i--;
        }
        i=i+cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 hang doc
        cnt=cnt1=cnt2=0;
        while(j<=row&&b[i][j].getText()==s){
            cnt1++;
            j=j+1;
        }
        j=j-cnt1;
        while(j>=2&&b[i][j-1].getText()==s){
            cnt2++;
            j--;
        }
        j=j+cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 duong cheo

        cnt=cnt1=cnt2=0;
        //5 duong cheo xuong
        while(j<=row&&i<=col&&b[i][j].getText()==s){
            cnt1++;
            i++;
            j++;
        }
        i=i-cnt1;
        j-=cnt1;
        while(i>=2&&j>=2&&b[i-1][j-1].getText()==s){
            cnt2++;
            i--;
            j--;
        }
        i=i+cnt2;
        j+=cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}

        //5 duong cheo len
        cnt=cnt1=cnt2=0;
        while(i>=1&&j<=row&&b[i][j].getText()==s){
            cnt1++;
            i--;
            j++;
        }
        i+=cnt1;
        j-=cnt1;
        while(i<col&&j>=2&&b[i+1][j-1].getText()==s){
            cnt2++;
            i++;
            j--;
        }
        i=i-cnt2;
        j+=cnt2;
        cnt=cnt1+cnt2;

        if(cnt>=5) {
            win=true;
            return true;}
        else return false;

    }
