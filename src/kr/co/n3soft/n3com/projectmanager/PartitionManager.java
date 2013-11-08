package kr.co.n3soft.n3com.projectmanager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import kr.co.n3soft.n3com.model.activity.SubPartitonModel;
import kr.co.n3soft.n3com.model.comm.PortModel;

public class PartitionManager {
    public PartitionManager() {
    }

    private java.util.ArrayList partitions = new java.util.ArrayList();

    public java.util.ArrayList getPartitions() {
        return this.partitions;
    }

    public void setPartitions(java.util.ArrayList p) {
        this.partitions = p;
    }

    public void addPartition(SubPartitonModel partition) {
//        partition.setLocation(new Point(partition.getLocation().x, partition.getLocation().y));//PKY 08052101 S 컨테이너에서 그룹으로 변경
        partitions.add(partition);
    }

    public void removePartition(SubPartitonModel partition) {
        partitions.remove(partition);
    }

    public void setSizePartitions(Dimension d) {
        for (int i = 0; i < this.partitions.size(); i++) {
            SubPartitonModel subPartitonModel = (SubPartitonModel)this.partitions.get(i);
            if (subPartitonModel != null) {
                subPartitonModel.setSize(d.getCopy());
            }
        }
    }
}
