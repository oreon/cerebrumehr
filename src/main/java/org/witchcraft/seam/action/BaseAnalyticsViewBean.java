package org.witchcraft.seam.action;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.primefaces.component.chart.Chart;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.PieChartModel;
import org.witchcraft.base.entity.AnalyticsData;



/**
 * Base class for grouping analytics 
 * @author jag singh
 *
 */
public abstract class BaseAnalyticsViewBean   {
	
	@In
	EntityManager entityManager;
	
	
	public abstract String[] getGroupByFlds();
	

	private TreeNode groupsTree = null;


	private TreeNode selectedNode;
	
	//private int count ;

	private DualListModel<String> selecteGroupFields;

	@javax.annotation.PostConstruct
	public void init() {
		
		//if (!isPostBack()) {
			List<String> src = new ArrayList<String>();
			List<String> tgt = Arrays.asList(getGroupByFlds());

			selecteGroupFields = new DualListModel<String>(src, tgt);
		//}
	}

	private void tradd(List<Object> list, TreeNode parent) {
		if (list.size() >= 2) {

			AnalyticsData adata = null;

			if (list.get(0) == null)
				return;

			String firstElement = list.get(0).toString();

			if (list.size() == 2) {
				adata = new AnalyticsData(firstElement, (Long) list.get(1));
			} else {
				adata = new AnalyticsData(firstElement, 0L);
			}

			TreeNode child = findElement(parent.getChildren(), adata);

			if (child == null) {
				child = new DefaultTreeNode(adata, parent);
				//child.setRowKey( String.valueOf(++count)  );
				// System.out.println(" adding " + adata.getName() + " " +
				// parent);
			}

			tradd(list.subList(1, list.size()), child);

			updateTotals(parent);

		}
	}

	private static void updateTotals(TreeNode current) {
		List<TreeNode> children = current.getChildren();
		List<AnalyticsData> dataChildren = new ArrayList<AnalyticsData>();
		AnalyticsData currentData = (AnalyticsData) current.getData();

		long total = 0;

		for (TreeNode treeNode : children) {

			AnalyticsData childData = ((AnalyticsData) treeNode.getData());
			total += childData.getSize();
			dataChildren.add(childData);
		}

		currentData.setSize(total);
		currentData.setChildren(dataChildren);
	}

	private static TreeNode findElement(List<TreeNode> list,
			AnalyticsData dataToFind) {
		for (TreeNode treeNode : list) {
			AnalyticsData current = (AnalyticsData) treeNode.getData();

			if ((current).equals(dataToFind)) {

				return treeNode;
			}
		}
		return null;
	}
	
	
	

	public TreeNode fetchTree() {

		if (groupsTree == null) {

			groupsTree = new DefaultTreeNode(new AnalyticsData("root", 0L), null);

			List<List<Object>> mytuples = findGroupedRecords();

			if (mytuples != null) {

				for (List<Object> list : mytuples) {
					tradd(list, groupsTree);
				}

			}

			if (selectedNode == null)
				selectedNode = groupsTree;

		}

		return groupsTree;
	}
	
	public void updateTree(){
		groupsTree = null;
	}

	public DualListModel<String> getSelecteGroupFields() {
		return selecteGroupFields;
	}

	public void setSelecteGroupFields(DualListModel<String> selecteGroupField) {
		this.selecteGroupFields = selecteGroupField;
	}

	public List<List<Object>> findGroupedRecords() {

		List<List<Object>> mytuples = new ArrayList<List<Object>>();

		if (selecteGroupFields == null || selecteGroupFields.getTarget().isEmpty()) {
			// selecteGroupField.addAll(getListGroupByFields());
			return mytuples;
		}

		StringBuilder sb = new StringBuilder();

		for (String grpField : selecteGroupFields.getTarget()) {
			sb.append("p." + grpField + ",");
		}

		System.out.println("querying with - " + sb.toString());

		// remove trailing comma
		sb.deleteCharAt(sb.length() - 1);

		String qry = "select  %s,  count(p.id) from " + getEntityName()  + " p "
				+ " group by %s order by %s  ";

		qry = String.format(qry, sb.toString(), sb.toString(), sb.toString());

		String datepart = " where u.dateCreated > '20131001'";

		Query query = entityManager.createQuery(qry);

		List<Object[]> listExpected = query.getResultList();

		for (Object[] strings : listExpected) {
			mytuples.add(Arrays.asList(strings));
		}

		return mytuples;

	}

	public abstract String getEntityName();

	public void itemSelect(ItemSelectEvent event) {

		Chart chart = (Chart) event.getSource();

		PieChartModel model = (PieChartModel) chart.getModel();

		Number cData = model.getData().get(event.getItemIndex());

		String id = chart.getId();

		if (id.equals("root")) {
			groupsTree.getChildren().get(event.getItemIndex());
		} else {

		}

	}

	public List<AnalyticsData> fetchChildPieCharts() {
		return null;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		if (selectedNode == null)
			return;

		if ( selectedNode.getChildren().isEmpty())
			selectedNode = selectedNode.getParent();
		this.selectedNode = selectedNode;
	}
	
	public TreeNode getGroupsTree() {
		if(groupsTree == null){
			fetchTree();
		}
		return groupsTree;
	}

	public void setGroupsTree(TreeNode groupsTree) {
		this.groupsTree = groupsTree;
	}

	
}

