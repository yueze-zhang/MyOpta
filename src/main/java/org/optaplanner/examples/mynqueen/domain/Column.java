package org.optaplanner.examples.mynqueen.domain;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;
/**
 * @program: optaplanner-examples
 * @description: 列对象
 * @author: Zhang
 * @create: 2019-09-24 15:46
 */
@XStreamAlias("Column")
public class Column extends AbstractPersistable{
    private int index;

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) { this.index = index; }

    @Override
    public String toString() {
        return "Column-" + index;
    }
}
