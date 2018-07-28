package com.example.manujohn.cs3100_3;

public class tetramino
{
    BasicBlock curr[];

    public tetramino(tetramino T, GameState G)
    {
        G.ctr=G.ctr+1;
        curr = new BasicBlock[4];
        for(int i=0;i<4;i++)
        {
            curr[i]=T.curr[i].copy();
            curr[i].tetra_id=G.ctr;
        }
        G.tetra_map.put(G.ctr,this);
    }

    public tetramino(int x, GameState G)
    {
        G.ctr=G.ctr+1;
        curr = new BasicBlock[4];

//				int type;
//				int colour;
//				int tetra_id;
//				Coordinate C;
//				int active;

        if(x==1)
        {
            curr[0] = new BasicBlock(1,1,G.ctr,new Coordinate(0,10),-1);
            curr[1] = new BasicBlock(1,1,G.ctr,new Coordinate(1,10),-1);
            curr[2] = new BasicBlock(1,1,G.ctr,new Coordinate(1,11),-1);
            curr[3] = new BasicBlock(1,1,G.ctr,new Coordinate(0,11),-1);
        }
        else if(x==2)
        {
            curr[0] = new BasicBlock(2,2,G.ctr,new Coordinate(0,10),-1);
            curr[1] = new BasicBlock(2,2,G.ctr,new Coordinate(0,11),-1);
            curr[2] = new BasicBlock(2,2,G.ctr,new Coordinate(1,10),-1);
            curr[3] = new BasicBlock(2,2,G.ctr,new Coordinate(2,10),-1);

        }
        else if(x==3)
        {
            curr[0] = new BasicBlock(3,3,G.ctr,new Coordinate(0,11),-1);
            curr[1] = new BasicBlock(3,3,G.ctr,new Coordinate(0,10),-1);
            curr[2] = new BasicBlock(3,3,G.ctr,new Coordinate(1,11),-1);
            curr[3] = new BasicBlock(3,3,G.ctr,new Coordinate(2,11),-1);

        }
        else if(x==4)
        {
            curr[0] = new BasicBlock(4,4,G.ctr,new Coordinate(1,10),-1);
            curr[1] = new BasicBlock(4,4,G.ctr,new Coordinate(0,10),-1);
            curr[2] = new BasicBlock(4,4,G.ctr,new Coordinate(1,11),-1);
            curr[3] = new BasicBlock(4,4,G.ctr,new Coordinate(2,10),-1);

        }
        else if(x==5)
        {
            curr[0] = new BasicBlock(5,5,G.ctr,new Coordinate(1,11),-1);
            curr[1] = new BasicBlock(5,5,G.ctr,new Coordinate(1,10),-1);
            curr[2] = new BasicBlock(5,5,G.ctr,new Coordinate(0,10),-1);
            curr[3] = new BasicBlock(5,5,G.ctr,new Coordinate(2,11),-1);
        }
        else if(x==6)
        {
            curr[0] = new BasicBlock(6,6,G.ctr,new Coordinate(1,11),-1);
            curr[1] = new BasicBlock(6,6,G.ctr,new Coordinate(0,11),-1);
            curr[2] = new BasicBlock(6,6,G.ctr,new Coordinate(1,10),-1);
            curr[3] = new BasicBlock(6,6,G.ctr,new Coordinate(2,10),-1);

        }
        else if(x==7)
        {
            curr[0] = new BasicBlock(7,7,G.ctr,new Coordinate(0,10),-1);
            curr[1] = new BasicBlock(7,7,G.ctr,new Coordinate(1,10),-1);
            curr[2] = new BasicBlock(7,7,G.ctr,new Coordinate(2,10),-1);
            curr[3] = new BasicBlock(7,7,G.ctr,new Coordinate(3,10),-1);
        }


        G.tetra_map.put(G.ctr,this);

        for (int i = 0; i < 4; i++) {
            if (BasicBlock.position(G.board,curr[i].C).active == -1)
                G.status = false;
        }

    }


    public boolean isConflict(Coordinate P,GameState G)
    {

        if(P.c<0 || P.c>=G.c || P.r<0 || P.r>=G.r)
            return true;

        if( BasicBlock.position(G.board,P).active==-1)
            return true;

        return false;
    }

    public boolean shiftDown(GameState G) {


        for(int i=0;i<4;i++)
        {
            if(curr[i].active==0)
                continue;

            if(isConflict(Coordinate.add(curr[i].C,new Coordinate(1,0)),G))
                return false;
        }

        for(int i=0;i<4;i++)
            curr[i].C.r++;

        return true;
    }

    public boolean Left(GameState G) {
        for(int i=0;i<4;i++)
        {
            if(curr[i].active==0)
                continue;

            if(isConflict(Coordinate.sub(curr[i].C,new Coordinate(0,1)),G))
                return false;
        }

        for(int i=0;i<4;i++)
            curr[i].C.c--;

        return true;
    }

    public boolean Right(GameState G) {
        for(int i=0;i<4;i++)
        {
            if(curr[i].active==0)
                continue;

            if(isConflict(Coordinate.add(curr[i].C,new Coordinate(0,1)),G))
                return false;
        }

        for(int i=0;i<4;i++)
            curr[i].C.c++;


        return true;
    }

    public boolean Rotate(GameState G) {

        if(curr[0].type==1)
            return true;
        else
        {
            for(int i=0;i<4;i++)
            {
                if(curr[i].active==0)
                    continue;

                if(isConflict(Coordinate.add(Coordinate.iota(Coordinate.sub(curr[i].C,curr[0].C)),curr[0].C),G))
                    return false;
            }

            for(int i=0;i<4;i++)
                curr[i].C = Coordinate.add(Coordinate.iota(Coordinate.sub(curr[i].C,curr[0].C)),curr[0].C);
            return true;

        }

    }

    public void update(GameState G)
    {
        for(int i=0;i<4;i++)
        {
            if(curr[i].active==0)
                continue;

            BasicBlock.position(G.board,curr[i].C).Assign_V(G.falling.curr[i]);
        }
    }
    public boolean is_tetra_present(Coordinate D, tetramino T)
    {
        for(int i=0;i<4;i++)
        {
            if(T.curr[i].active==0)
                continue;

            if(Coordinate.Isequal(T.curr[i].C,D))
                return true;

        }

        return false;
    }
    public void shift_till_bottom(GameState G, tetramino T)
    {
        boolean left = true;
        while(left)
        {
            int flag=0;
            left = false;

            for(int i=0;i<4;i++)
            {
                if(T.curr[i].active==0 )
                    continue;

                if(is_tetra_present(Coordinate.add(T.curr[i].C,new Coordinate(1,0)),T))
                    continue;

                if(isConflict(Coordinate.add(T.curr[i].C,new Coordinate(1,0)),G))
                    flag=1;
            }

            if(flag==0)
            {
                for(int i=0;i<4;i++)
                {
                    if(T.curr[i].active==0)
                        continue;

                    BasicBlock.position(G.board,T.curr[i].C).Assign_V(-1,-1,-1,T.curr[i].C,0);


                    T.curr[i].C.r++;
                }

                for(int i=0;i<4;i++)
                {
                    if(T.curr[i].active==0)
                        continue;

                    BasicBlock.position(G.board,T.curr[i].C).Assign_V(T.curr[i]);

                }
                left = true;
            }
        }
    }

    public void adjust_the_matrix(GameState G)
    {
        for(int i=G.r-1;i>=0;i--)
        {
            for(int j=0;j<G.c;j++)
            {
                tetramino T = (G.tetra_map).get((G.board[i][j].tetra_id));

                if(T!=null)
                    shift_till_bottom(G,T);
            }
        }
    }

    public void line_remove(GameState G)
    {
        boolean left = true;
        while(left)
        {

            left = false;
            for(int i=G.r-1;i>=0;i--)
            {
                int flag=0;
                for(int j=0;j<G.c;j++)
                {
                    if(G.board[i][j].active==0)
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag==0)
                {
                    left = true;
                    for(int j=0;j<G.c;j++)
                    {
                        G.board[i][j].active=0;

                        tetramino T = (G.tetra_map).get((G.board[i][j].tetra_id));

                        if(T==null)
                            continue;

                        for(int k=0;k<4;k++)
                        {
                            if(T.curr[k].active==0)
                                continue;

                            if(T.curr[k].C.r==i && T.curr[k].C.c==j )
                            {
                                T.curr[k].active=0;

                                tetramino T_U= new tetramino(T,G);
                                tetramino T_B= new tetramino(T,G);

                                for(int s=0;s<4;s++)
                                {
                                    if(T_U.curr[s].C.r>=T.curr[k].C.r)
                                    {
                                        T_U.curr[s].active=0;
                                    }
                                    else
                                    {
                                        BasicBlock.position(G.board,T_U.curr[s].C).tetra_id= T_U.curr[s].tetra_id;
                                    }
                                }
                                for(int s=0;s<4;s++)
                                {
                                    if(T_B.curr[s].C.r<=T.curr[k].C.r)
                                    {
                                        T_B.curr[s].active=0;
                                    }
                                    else
                                    {
                                        BasicBlock.position(G.board,T_B.curr[s].C).tetra_id= T_B.curr[s].tetra_id;
                                    }
                                }

                                G.tetra_map.remove(T.curr[k].tetra_id);
                                T = null;
                                break;
                            }

                        }
                    }
                    adjust_the_matrix(G);
                    G.score++;
                    break;
                }
            }
        }
    }
}
