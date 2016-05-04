package spinat.plsqlparser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class Test11gParser {
	
	public Test11gParser() {
    }

    Seq scan(String s) {
        ArrayList<Token> a = Scanner.scanAll(s);
        ArrayList<Token> r = new ArrayList<>();
        for (Token t : a) {
            if (Scanner.isRelevant(t)) {
                r.add(t);
            }
        }
        return new Seq(r);
    }

    public void tpa(Pa p, String s) {
        Seq seq = scan(s);
        Res r = p.pa(seq);
        assertNotNull(r);
        System.out.println(r.v);
        assertTrue(r.next.head().ttype == TokenType.TheEnd || r.next.head().ttype == TokenType.Div);
    }
    
    @Test
    public void testCommitStatement() {
        Parser p = new Parser();
        tpa(p.pStatement, "commit");
        tpa(p.pStatement, "commit work");
        tpa(p.pStatement, "commit comment 'bbb'");
        tpa(p.pStatement, "commit work write batch");
        tpa(p.pStatement, "commit write batch");
        tpa(p.pStatement, "commit write batch nowait");
        tpa(p.pStatement, "commit comment 'bbb' write immediate");
        tpa(p.pStatement, "commit comment 'bbb' write immediate wait");
        tpa(p.pStatement, "commit work comment 'bbb' write batch nowait");
        tpa(p.pStatement, "commit force 'aaa'");
        tpa(p.pStatement, "commit force 'bbb' , 1");
        tpa(p.pStatement, "commit work force 'bbb' , 2");
    }
}
