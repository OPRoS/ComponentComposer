package kr.co.n3soft.n3com.project.dialog;

import java.util.ArrayList;
import java.util.Vector;

import kr.co.n3soft.n3com.dnd.TreeSimpleFactory;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.StrcuturedPackageTreeModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.DependencyLineModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;
import kr.co.n3soft.n3com.model.usecase.GeneralizeLineModel;
import kr.co.n3soft.n3com.model.usecase.RealizeLineModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class PatternDialog extends Dialog {
	 private TreeViewer viewer;
	 Group PatternsType;
	 int patternType=-1;
	 private TableViewer patternViewer;
		private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;
		private static final int OK_ID = 0;
		private static final int CANCEL_ID = 1;
//	 private static final String TAG_PROPERTY = "Tag";
//		private static final String TAGVALUE_PROPERTY = "value";
		 private static final String PATTERN_PROPERTY1 = "Type";
			private static final String PATTERN_PROPERTY2 = "Class";
	 public PatternDialog(Shell parentShell) {
	        super(parentShell);
	    }
	 protected Control createDialogArea(Composite parent) {
	        Composite comp = (Composite)super.createDialogArea(parent);
	        
	        comp.getShell().setText(N3Messages.DIALOG_PATTERN);//20080717 KDI s			 
			comp.getShell().setImage(ProjectManager.getInstance().getImage(207));//20080724 KDI s
						
			
	        GridLayout layout = (GridLayout)comp.getLayout();
            layout.numColumns = 3;
	        GridData data1 = new GridData(GridData.FILL_BOTH);
	        Group description;
	        StringBuffer descClass;
	        Label descriptionField = null;
//            data1.horizontalSpan = 2;
            PatternsType = new Group(comp, SWT.SHADOW_ETCHED_IN);
            PatternsType.setText(N3Messages.DIALOG_DIAGRAMTYPE);//2008040301 PKY S 
            //			type.setBounds(10, 10, 400, 300);
           
            PatternsType.setLayoutData(data1);
            viewer = new TreeViewer(PatternsType, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
            viewer.setContentProvider(new ViewContentProvider());
            viewer.setLabelProvider(new ViewLabelProvider());
            //			viewer.setSorter(new NameSorter());
            viewer.setInput(this.initialize());
            viewer.getTree().pack();
            viewer.getTree().setBounds(10, 30, 250, 400);
            PatternsType.pack();
            viewer.addSelectionChangedListener(
                    new ISelectionChangedListener() {
                        public void selectionChanged(SelectionChangedEvent event) {
                            IStructuredSelection sel = (IStructuredSelection)event.getSelection();
                            Object obj = sel.getFirstElement();
                            if (obj instanceof TreeObject) {
                                TreeObject treeObject = (TreeObject)obj;
                                patternType = treeObject.getType();
                                if(patternType>100){
                                	patternViewer.getTable().removeAll();
                                	definePattern(patternViewer,patternType);
                                }
//                				tags.add(newItem);
                                //						descriptionField.setText(descClass.toString());
                            }
                        }
                    });
            GridData data2 = new GridData(GridData.FILL_BOTH);
            description = new Group(comp, SWT.SHADOW_ETCHED_IN);
            
            final Table table = new Table(description, SWT.FULL_SELECTION);
//            data1 = new GridData(GridData.FILL_HORIZONTAL);
////			data1.horizontalSpan = 2;
//			data1.heightHint = 200;
//			data1.widthHint = 400;
//			table.setLayoutData(data1);
            patternViewer = buildAndLayoutTagTable(table);
    		this.attachLabelProviderTag(patternViewer);
    		this.attachCellEditorsTag(patternViewer, table);
    		patternViewer.getTable().setBounds(10, 30, 400, 400);
            
            description.setText(N3Messages.POPUP_DESCRIPTION);//2008040302 PKY S ;
//            descriptionField = new Label(description, SWT.LEFT);
//            //			descriptionField.setSize(500, 300);
//            descriptionField.pack();
//            descriptionField.setBounds(10, 25, 500, 400);
//            descriptionField.setImage(this.getDiagramDescImage("usecasedesc.bmp"));
            description.setLayoutData(data2);
            description.pack();
           
	        return comp;
	 }
	 
	 private TableViewer buildAndLayoutTagTable(final Table table) {
			TableViewer tableViewer = new TableViewer(table);
			TableLayout layout = new TableLayout();
			layout.addColumnData(new ColumnWeightData(200, 100, true));
			layout.addColumnData(new ColumnWeightData(200, 100, true));
			table.setLayout(layout);
			TableColumn tagColumn = new TableColumn(table, SWT.LEFT);
			tagColumn.setText(this.PATTERN_PROPERTY1);
			TableColumn valueColumn = new TableColumn(table, SWT.LEFT);
			valueColumn.setText(this.PATTERN_PROPERTY2);

			table.setHeaderVisible(true);
			return tableViewer;
		}



		private void attachLabelProviderTag(TableViewer viewer) {
			viewer.setLabelProvider(
					new ITableLabelProvider() {
						public Image getColumnImage(Object element, int columnIndex) {
							if(element instanceof DetailPropertyTableItem){
								DetailPropertyTableItem si = (DetailPropertyTableItem)element;

							}

							return null;

						}
						public String getColumnText(Object element, int columnIndex) {
							switch (columnIndex) {
							case 0:
								String value = ((DetailPropertyTableItem)element).tapName;
								return value;
							case 1:
								value = ((DetailPropertyTableItem)element).desc;
								return value;

							default:
								return "Invalid column: " + columnIndex;
							}
						}
						public void addListener(ILabelProviderListener listener) {
						}
						public void dispose() {
						}
						public boolean isLabelProperty(Object element, String property) {
							return false;
						}
						public void removeListener(ILabelProviderListener lpl) {
						}
					});
		}
		
		
		public void definePattern(TableViewer patternViewer,int defineValue){
			if(defineValue==206){
				DetailPropertyTableItem itemAbstractFactory = new DetailPropertyTableItem();

				itemAbstractFactory.tapName = "AbstractFactory";
				itemAbstractFactory.desc ="AbstractFactory"; 
				patternViewer.add(itemAbstractFactory);
				DetailPropertyTableItem itemAbstractProduct = new DetailPropertyTableItem();
				itemAbstractProduct.tapName = "AbstractProduct";
				itemAbstractProduct.desc ="AbstractProduct"; 
				patternViewer.add(itemAbstractProduct);
				DetailPropertyTableItem itemConcreteFactory = new DetailPropertyTableItem();
				itemConcreteFactory.tapName = "ConcreteFactory";
				itemConcreteFactory.desc ="ConcreteFactory"; 
				patternViewer.add(itemConcreteFactory);
				DetailPropertyTableItem itemConcreteProduct = new DetailPropertyTableItem();
				itemConcreteProduct.tapName = "ConcreteProduct";
				itemConcreteProduct.desc ="ConcreteProduct"; 
				patternViewer.add(itemConcreteProduct);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);
			}
			else if(defineValue==207){
				DetailPropertyTableItem itemAdaptee = new DetailPropertyTableItem();
				itemAdaptee.tapName = "Adaptee";
				itemAdaptee.desc ="Adaptee"; 
				patternViewer.add(itemAdaptee);
				DetailPropertyTableItem itemTarget = new DetailPropertyTableItem();
				itemTarget.tapName = "Target";
				itemTarget.desc ="Target"; 
				patternViewer.add(itemTarget);
				DetailPropertyTableItem itemAdapter = new DetailPropertyTableItem();
				itemAdapter.tapName = "Adapter";
				itemAdapter.desc ="Adapter"; 
				patternViewer.add(itemAdapter);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);
			}
			else if(defineValue==208){
				DetailPropertyTableItem itemAbstraction = new DetailPropertyTableItem();
				itemAbstraction.tapName = "Abstraction";
				itemAbstraction.desc ="Abstraction"; 
				patternViewer.add(itemAbstraction);
				DetailPropertyTableItem itemRefinedAbstraction = new DetailPropertyTableItem();
				itemRefinedAbstraction.tapName = "RefinedAbstraction";
				itemRefinedAbstraction.desc ="RefinedAbstraction"; 
				patternViewer.add(itemRefinedAbstraction);
				DetailPropertyTableItem itemImplementor = new DetailPropertyTableItem();
				itemImplementor.tapName = "Implementor";
				itemImplementor.desc ="Implementor"; 
				patternViewer.add(itemImplementor);
				DetailPropertyTableItem itemConcreteClient = new DetailPropertyTableItem();
				itemConcreteClient.tapName = "ConcreteImplementor";
				itemConcreteClient.desc ="ConcreteImplementor"; 
				patternViewer.add(itemConcreteClient);
			}
			else if(defineValue==209){
				DetailPropertyTableItem itemBuilder = new DetailPropertyTableItem();
				itemBuilder.tapName = "Builder";
				itemBuilder.desc ="Builder"; 
				patternViewer.add(itemBuilder);
				DetailPropertyTableItem itemDirector = new DetailPropertyTableItem();
				itemDirector.tapName = "Director";
				itemDirector.desc ="Director"; 
				patternViewer.add(itemDirector);
				DetailPropertyTableItem itemConcreteBuilder = new DetailPropertyTableItem();
				itemConcreteBuilder.tapName = "ConcreteBuilder";
				itemConcreteBuilder.desc ="ConcreteBuilder"; 
				patternViewer.add(itemConcreteBuilder);
				DetailPropertyTableItem itemProduct = new DetailPropertyTableItem();
				itemProduct.tapName = "Product";
				itemProduct.desc ="Product"; 
				patternViewer.add(itemProduct);
			}
			else if(defineValue==210){
				DetailPropertyTableItem itemHandler = new DetailPropertyTableItem();
				itemHandler.tapName = "Handler";
				itemHandler.desc ="Handler"; 
				patternViewer.add(itemHandler);
				DetailPropertyTableItem itemConcreteHandler = new DetailPropertyTableItem();
				itemConcreteHandler.tapName = "ConcreteHandler";
				itemConcreteHandler.desc ="ConcreteHandler"; 
				patternViewer.add(itemConcreteHandler);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);
			}else if(defineValue==211){
				DetailPropertyTableItem itemCommand = new DetailPropertyTableItem();
				itemCommand.tapName = "Command";
				itemCommand.desc ="Command"; 
				patternViewer.add(itemCommand);
				DetailPropertyTableItem itemConcreteCommand = new DetailPropertyTableItem();
				itemConcreteCommand.tapName = "ConcreteCommand";
				itemConcreteCommand.desc ="ConcreteCommand"; 
				patternViewer.add(itemConcreteCommand);
				DetailPropertyTableItem itemInvoker = new DetailPropertyTableItem();
				itemInvoker.tapName = "Invoker";
				itemInvoker.desc ="Invoker"; 
				patternViewer.add(itemInvoker);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);
				DetailPropertyTableItem itemReceiver = new DetailPropertyTableItem();
				itemReceiver.tapName = "Receiver";
				itemReceiver.desc ="Receiver"; 
				patternViewer.add(itemReceiver);

			}else if(defineValue==212){
				DetailPropertyTableItem itemComponent = new DetailPropertyTableItem();
				itemComponent.tapName = "Component";
				itemComponent.desc ="Component"; 
				patternViewer.add(itemComponent);
				DetailPropertyTableItem itemLeaf = new DetailPropertyTableItem();
				itemLeaf.tapName = "Leaf";
				itemLeaf.desc ="Leaf"; 
				patternViewer.add(itemLeaf);
				DetailPropertyTableItem itemComposite = new DetailPropertyTableItem();
				itemComposite.tapName = "Composite";
				itemComposite.desc ="Composite"; 
				patternViewer.add(itemComposite);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);					

			}else if(defineValue==213){
				DetailPropertyTableItem itemDecorator = new DetailPropertyTableItem();
				itemDecorator.tapName = "Decorator";
				itemDecorator.desc ="Decorator"; 
				patternViewer.add(itemDecorator);
				DetailPropertyTableItem itemConcreteDecorator = new DetailPropertyTableItem();
				itemConcreteDecorator.tapName = "ConcreteDecorator";
				itemConcreteDecorator.desc ="ConcreteDecorator"; 
				patternViewer.add(itemConcreteDecorator);
				DetailPropertyTableItem itemComponent = new DetailPropertyTableItem();
				itemComponent.tapName = "Component";
				itemComponent.desc ="Component"; 
				patternViewer.add(itemComponent);		
				DetailPropertyTableItem itemConcreateComponent = new DetailPropertyTableItem();
				itemConcreateComponent.tapName = "ConcreateComponent";
				itemConcreateComponent.desc ="ConcreateComponent"; 
				patternViewer.add(itemConcreateComponent);	
			}else if(defineValue==214){
				DetailPropertyTableItem itemFacade = new DetailPropertyTableItem();
				itemFacade.tapName = "Facade";
				itemFacade.desc ="Facade"; 
				patternViewer.add(itemFacade);
				DetailPropertyTableItem itemSubsystemClasses = new DetailPropertyTableItem();
				itemSubsystemClasses.tapName = "Subsystem Classes";
				itemSubsystemClasses.desc ="Subsystem Classes"; 
				patternViewer.add(itemSubsystemClasses);

			}else if(defineValue==215){
				DetailPropertyTableItem itemProduct = new DetailPropertyTableItem();
				itemProduct.tapName = "Product";
				itemProduct.desc ="Product"; 
				patternViewer.add(itemProduct);
				DetailPropertyTableItem itemConcreteProduct = new DetailPropertyTableItem();
				itemConcreteProduct.tapName = "ConcreteProduct";
				itemConcreteProduct.desc ="ConcreteProduct"; 
				patternViewer.add(itemConcreteProduct);
				DetailPropertyTableItem itemCreator = new DetailPropertyTableItem();
				itemCreator.tapName = "Creator";
				itemCreator.desc ="Creator"; 
				patternViewer.add(itemCreator);
				DetailPropertyTableItem itemConcreteCreator = new DetailPropertyTableItem();
				itemConcreteCreator.tapName = "ConcreteCreator";
				itemConcreteCreator.desc ="ConcreteCreator"; 
				patternViewer.add(itemConcreteCreator);

			}else if(defineValue==216){
				DetailPropertyTableItem itemFlyweight = new DetailPropertyTableItem();
				itemFlyweight.tapName = "Flyweight";
				itemFlyweight.desc ="Flyweight"; 
				patternViewer.add(itemFlyweight);
				DetailPropertyTableItem itemConcreteFlyweight = new DetailPropertyTableItem();
				itemConcreteFlyweight.tapName = "ConcreteFlyweight";
				itemConcreteFlyweight.desc ="ConcreteFlyweight"; 
				patternViewer.add(itemConcreteFlyweight);
				DetailPropertyTableItem itemunsharedConcreteFlyweight = new DetailPropertyTableItem();
				itemunsharedConcreteFlyweight.tapName = "UnsharedConcreteFlyweight";
				itemunsharedConcreteFlyweight.desc ="unsharedConcreteFlyweight"; 
				patternViewer.add(itemunsharedConcreteFlyweight);
				DetailPropertyTableItem itemFlyweightFactory = new DetailPropertyTableItem();
				itemFlyweightFactory.tapName = "FlyweightFactory";
				itemFlyweightFactory.desc ="FlyweightFactory"; 
				patternViewer.add(itemFlyweightFactory);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);	

			}else if(defineValue==217){
				DetailPropertyTableItem itemAbstractExpression = new DetailPropertyTableItem();
				itemAbstractExpression.tapName = "AbstractExpression";
				itemAbstractExpression.desc ="AbstractExpression"; 
				patternViewer.add(itemAbstractExpression);
				DetailPropertyTableItem itemTerminalExpression = new DetailPropertyTableItem();
				itemTerminalExpression.tapName = "TerminalExpression";
				itemTerminalExpression.desc ="TerminalExpression"; 
				patternViewer.add(itemTerminalExpression);
				DetailPropertyTableItem itemNonterminalExpression = new DetailPropertyTableItem();
				itemNonterminalExpression.tapName = "NonterminalExpression";
				itemNonterminalExpression.desc ="NonterminalExpression"; 
				patternViewer.add(itemNonterminalExpression);
				DetailPropertyTableItem itemContext = new DetailPropertyTableItem();
				itemContext.tapName = "Context";
				itemContext.desc ="Context"; 
				patternViewer.add(itemContext);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);	

			}else if(defineValue==218){
				DetailPropertyTableItem itemIterator = new DetailPropertyTableItem();
				itemIterator.tapName = "Iterator";
				itemIterator.desc ="Iterator"; 
				patternViewer.add(itemIterator);
				DetailPropertyTableItem itemConcreteIterator = new DetailPropertyTableItem();
				itemConcreteIterator.tapName = "ConcreteIterator";
				itemConcreteIterator.desc ="ConcreteIterator"; 
				patternViewer.add(itemConcreteIterator);
				DetailPropertyTableItem itemAggregate = new DetailPropertyTableItem();
				itemAggregate.tapName = "Aggregate";
				itemAggregate.desc ="Aggregate"; 
				patternViewer.add(itemAggregate);
				DetailPropertyTableItem itemConcreteAggregate = new DetailPropertyTableItem();
				itemConcreteAggregate.tapName = "ConcreteAggregate";
				itemConcreteAggregate.desc ="ConcreteAggregate"; 
				patternViewer.add(itemConcreteAggregate);
			}else if(defineValue==219){
				DetailPropertyTableItem itemMediator = new DetailPropertyTableItem();
				itemMediator.tapName = "Mediator";
				itemMediator.desc ="Mediator"; 
				patternViewer.add(itemMediator);
				DetailPropertyTableItem itemConcreteMediator = new DetailPropertyTableItem();
				itemConcreteMediator.tapName = "ConcreteMediator";
				itemConcreteMediator.desc ="ConcreteMediator"; 
				patternViewer.add(itemConcreteMediator);
				DetailPropertyTableItem itemColleague = new DetailPropertyTableItem();
				itemColleague.tapName = "Colleague";
				itemColleague.desc ="Colleague"; 
				patternViewer.add(itemColleague);
			}else if(defineValue==220){
				DetailPropertyTableItem itemMemento = new DetailPropertyTableItem();
				itemMemento.tapName = "Memento";
				itemMemento.desc ="Memento"; 
				patternViewer.add(itemMemento);
				DetailPropertyTableItem itemOriginator = new DetailPropertyTableItem();
				itemOriginator.tapName = "Originator";
				itemOriginator.desc ="Originator"; 
				patternViewer.add(itemOriginator);
				DetailPropertyTableItem itemCaretaker = new DetailPropertyTableItem();
				itemCaretaker.tapName = "Caretaker";
				itemCaretaker.desc ="Caretaker"; 
				patternViewer.add(itemCaretaker);
			}else if(defineValue==221){
				DetailPropertyTableItem itemSubject = new DetailPropertyTableItem();
				itemSubject.tapName = "Subject";
				itemSubject.desc ="Subject"; 
				patternViewer.add(itemSubject);
				DetailPropertyTableItem itemObserver = new DetailPropertyTableItem();
				itemObserver.tapName = "Observer";
				itemObserver.desc ="Observer"; 
				patternViewer.add(itemObserver);
				DetailPropertyTableItem itemConcreteSubject = new DetailPropertyTableItem();
				itemConcreteSubject.tapName = "ConcreteSubject";
				itemConcreteSubject.desc ="ConcreteSubject"; 
				patternViewer.add(itemConcreteSubject);
				DetailPropertyTableItem itemConcreteObserver = new DetailPropertyTableItem();
				itemConcreteObserver.tapName = "ConcreteObserver";
				itemConcreteObserver.desc ="ConcreteObserver"; 
				patternViewer.add(itemConcreteObserver);
			}else if(defineValue==222){
				DetailPropertyTableItem itemPrototype = new DetailPropertyTableItem();
				itemPrototype.tapName = "Prototype";
				itemPrototype.desc ="Prototype"; 
				patternViewer.add(itemPrototype);
				DetailPropertyTableItem itemConcretePrototype = new DetailPropertyTableItem();
				itemConcretePrototype.tapName = "ConcretePrototype";
				itemConcretePrototype.desc ="ConcretePrototype"; 
				patternViewer.add(itemConcretePrototype);
				DetailPropertyTableItem itemClient = new DetailPropertyTableItem();
				itemClient.tapName = "Client";
				itemClient.desc ="Client"; 
				patternViewer.add(itemClient);
			}else if(defineValue==223){
				DetailPropertyTableItem itemProxy = new DetailPropertyTableItem();
				itemProxy.tapName = "Proxy";
				itemProxy.desc ="Proxy"; 
				patternViewer.add(itemProxy);
				DetailPropertyTableItem itemSubject = new DetailPropertyTableItem();
				itemSubject.tapName = "Subject";
				itemSubject.desc ="Subject"; 
				patternViewer.add(itemSubject);
				DetailPropertyTableItem itemRealSubject = new DetailPropertyTableItem();
				itemRealSubject.tapName = "RealSubject";
				itemRealSubject.desc ="RealSubject"; 
				patternViewer.add(itemRealSubject);
			}else if(defineValue==224){
				DetailPropertyTableItem itemSingleton = new DetailPropertyTableItem();
				itemSingleton.tapName = "Singleton";
				itemSingleton.desc ="Singleton"; 
				patternViewer.add(itemSingleton);

			}else if(defineValue==225){
				DetailPropertyTableItem itemState = new DetailPropertyTableItem();
				itemState.tapName = "State";
				itemState.desc ="State"; 
				patternViewer.add(itemState);
				DetailPropertyTableItem itemConcreteState = new DetailPropertyTableItem();
				itemConcreteState.tapName = "ConcreteState";
				itemConcreteState.desc ="ConcreteState"; 
				patternViewer.add(itemConcreteState);
				DetailPropertyTableItem itemContext = new DetailPropertyTableItem();
				itemContext.tapName = "Context";
				itemContext.desc ="Context"; 
				patternViewer.add(itemContext);
			}else if(defineValue==226){
				DetailPropertyTableItem itemStrategy = new DetailPropertyTableItem();
				itemStrategy.tapName = "Strategy";
				itemStrategy.desc ="Strategy"; 
				patternViewer.add(itemStrategy);
				DetailPropertyTableItem itemConcreteStrategy = new DetailPropertyTableItem();
				itemConcreteStrategy.tapName = "ConcreteStrategy";
				itemConcreteStrategy.desc ="ConcreteStrategy"; 
				patternViewer.add(itemConcreteStrategy);
				DetailPropertyTableItem itemContext = new DetailPropertyTableItem();
				itemContext.tapName = "Context";
				itemContext.desc ="Context"; 
				patternViewer.add(itemContext);
			}else if(defineValue==227){
				DetailPropertyTableItem itemAbstractClass = new DetailPropertyTableItem();
				itemAbstractClass.tapName = "AbstractClass";
				itemAbstractClass.desc ="AbstractClass"; 
				patternViewer.add(itemAbstractClass);
				DetailPropertyTableItem itemConcreteClass = new DetailPropertyTableItem();
				itemConcreteClass.tapName = "ConcreteClass";
				itemConcreteClass.desc ="ConcreteClass"; 
				patternViewer.add(itemConcreteClass);
			}else if(defineValue==228){
				DetailPropertyTableItem itemVisitor = new DetailPropertyTableItem();
				itemVisitor.tapName = "Visitor";
				itemVisitor.desc ="Visitor"; 
				patternViewer.add(itemVisitor);
				DetailPropertyTableItem itemConcreteVisitor = new DetailPropertyTableItem();
				itemConcreteVisitor.tapName = "ConcreteVisitor";
				itemConcreteVisitor.desc ="ConcreteVisitor"; 
				patternViewer.add(itemConcreteVisitor);
				DetailPropertyTableItem itemElement = new DetailPropertyTableItem();
				itemElement.tapName = "Element";
				itemElement.desc ="Element"; 
				patternViewer.add(itemElement);
				DetailPropertyTableItem itemConcreateElement = new DetailPropertyTableItem();
				itemConcreateElement.tapName = "ConcreateElement";
				itemConcreateElement.desc ="ConcreateElement"; 
				patternViewer.add(itemConcreateElement);
				DetailPropertyTableItem itemObjectStruture = new DetailPropertyTableItem();
				itemObjectStruture.tapName = "ObjectStruture";
				itemObjectStruture.desc ="ObjectStruture"; 
				patternViewer.add(itemObjectStruture);
			}else if(defineValue==230){
				DetailPropertyTableItem itemEntityBean = new DetailPropertyTableItem();
				itemEntityBean.tapName = "EntityBean";
				itemEntityBean.desc ="EntityBean"; 
				patternViewer.add(itemEntityBean);
				DetailPropertyTableItem itemEntityHome = new DetailPropertyTableItem();
				itemEntityHome.tapName = "EntityHome";
				itemEntityHome.desc ="EntityHome"; 
				patternViewer.add(itemEntityHome);
				DetailPropertyTableItem itemRemoteInterface = new DetailPropertyTableItem();
				itemRemoteInterface.tapName = "Remote Interface";
				itemRemoteInterface.desc ="Entity"; 
				patternViewer.add(itemRemoteInterface);
				DetailPropertyTableItem itemEJBPrimaryKeyClass = new DetailPropertyTableItem();
				itemEJBPrimaryKeyClass.tapName = "EJB Primary Key Class";
				itemEJBPrimaryKeyClass.desc ="Entity PK"; 
				patternViewer.add(itemEJBPrimaryKeyClass);
				
			}else if(defineValue==231){
				DetailPropertyTableItem itemClassName = new DetailPropertyTableItem();
				itemClassName.tapName = "ClassName";
				itemClassName.desc ="MessageDrivenBean"; 
				patternViewer.add(itemClassName);
	
			}else if(defineValue==232){
				DetailPropertyTableItem itemSessionBean = new DetailPropertyTableItem();
				itemSessionBean.tapName = "SessionBean";
				itemSessionBean.desc ="SessionBean"; 
				patternViewer.add(itemSessionBean);
				DetailPropertyTableItem itemSessionHome = new DetailPropertyTableItem();
				itemSessionHome.tapName = "HomeInterface";
				itemSessionHome.desc ="SessionHome"; 
				patternViewer.add(itemSessionHome);
				DetailPropertyTableItem itemRemoteInterface = new DetailPropertyTableItem();
				itemRemoteInterface.tapName = "RemoteInterface";
				itemRemoteInterface.desc ="Session"; 
				patternViewer.add(itemRemoteInterface);
			}
			


		}

		
		public void definePatternDiagram(int defineValue,TableItem [] item){
			try{
				//ABSTRACT_FACTORY
			if(defineValue==206){
//				patternViewer
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				FinalClassModel umAf = new FinalClassModel();
				umAf.setName("AbstractFactory");
				java.util.ArrayList umAfOper = new ArrayList();
				OperationEditableTableItem opi = new OperationEditableTableItem();
				opi.stype = "void";
				opi.name = "CreateProduct";
				opi.scope = 0;
				umAfOper.add(opi);
				umAf.getClassModel().setOperations(umAfOper);
				FinalClassModel umAp = new FinalClassModel();
				umAp.setName("AbstractProduct");
				FinalClassModel umCf = new FinalClassModel();
				umCf.setName("ConcreteFactory");
				FinalClassModel umCp = new FinalClassModel();
				umCp.setName("ConcreteProduct");
				
				for(int i=0; i<item.length;i++){

					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
								
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
							}
						
						}else{
							this.makeTreeModel(umClient, n3EditorDiagramModel);
						}
					}
					else if(item[i].getText().equals("AbstractFactory")){
						if(!item[i].getText(1).equals("AbstractFactory")){
							FinalClassModel model=modelMatch(umAf, item[i].getText(1));							
							if(model!=null){
								umAf=diagramInModel(model,umAf,n3EditorDiagramModel);
							}else{
								umAf.setName(item[i].getText(1));
								this.makeTreeModel(umAf, n3EditorDiagramModel);

							}
						}else{
							umAf.setName(item[i].getText(1));
							this.makeTreeModel(umAf, n3EditorDiagramModel);
							 
						}			
					}
					else if(item[i].getText().equals("AbstractProduct")){
						if(!item[i].getText(1).equals("AbstractProduct")){
							FinalClassModel model=modelMatch(umAp, item[i].getText(1));							
							if(model!=null){
								umAp=diagramInModel(model,umAp,n3EditorDiagramModel);
							}else{
								umAp.setName(item[i].getText(1));
								this.makeTreeModel(umAp, n3EditorDiagramModel);
								 
							}
						}else{
							umAp.setName(item[i].getText(1));
							this.makeTreeModel(umAp, n3EditorDiagramModel);
							 
						}			
					}
					else if(item[i].getText().equals("ConcreteFactory")){
						if(!item[i].getText(1).equals("ConcreteFactory")){
							FinalClassModel model=modelMatch(umCf, item[i].getText(1));							
							if(model!=null){
								umCf=diagramInModel(model,umCf,n3EditorDiagramModel);
							}else{
								umCf.setName(item[i].getText(1));
								this.makeTreeModel(umCf, n3EditorDiagramModel);
								 
							}
						}else{
							umCf.setName(item[i].getText(1));
							this.makeTreeModel(umCf, n3EditorDiagramModel);
							 
						}			
					}
					else if(item[i].getText().equals("ConcreteProduct")){
						if(!item[i].getText(1).equals("ConcreteProduct")){
							FinalClassModel model=modelMatch(umCp, item[i].getText(1));							
							if(model!=null){
								umCp=diagramInModel(model,umCp,n3EditorDiagramModel);
							}else{
								umCp.setName(item[i].getText(1));
								this.makeTreeModel(umCp, n3EditorDiagramModel);
								 
							}
						}else{
							umCp.setName(item[i].getText(1));
							this.makeTreeModel(umCp, n3EditorDiagramModel);
							 
						}			
					}
				}
				
//	
//				this.makeTreeModel(umAf, n3EditorDiagramModel);
//				this.makeTreeModel(umAp, n3EditorDiagramModel);
//				this.makeTreeModel(umCf, n3EditorDiagramModel);
//				this.makeTreeModel(umCp, n3EditorDiagramModel);
				umClient.setLocation(new Point(300,100));
				umAf.setLocation(new Point(200,200));
				umAp.setLocation(new Point(400,200));
				umCf.setLocation(new Point(200,300));
				umCp.setLocation(new Point(400,300));
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umAf);
				n3EditorDiagramModel.addChild(umAp);
				n3EditorDiagramModel.addChild(umCf);
				n3EditorDiagramModel.addChild(umCp);
				this.createAssociateLineModelModel(umClient, umAf, n3EditorDiagramModel);
				this.createAssociateLineModelModel(umClient, umAp, n3EditorDiagramModel);
				this.createGeneralizeLineModel(umCf, umAf, n3EditorDiagramModel);
				this.createGeneralizeLineModel(umCp, umAp, n3EditorDiagramModel);
				this.createDependencyLineModel(umCf, umCp, n3EditorDiagramModel);
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}
			//ADAPTER
			else if(defineValue==207){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				

				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				FinalClassModel umTarget = new FinalClassModel();
				umTarget.setName("Target");
				java.util.ArrayList umTargetOper = new ArrayList();
				OperationEditableTableItem opi = new OperationEditableTableItem();
				opi.stype = "void";
				opi.name = "Request";
				opi.scope = 0;
				umTargetOper.add(opi);
				umTarget.getClassModel().setOperations(umTargetOper);
				
				FinalClassModel umAdapter = new FinalClassModel();
				umAdapter.setName("Adapter");
				
				FinalClassModel umAdaptee = new FinalClassModel();
				umAdaptee.setName("Adaptee");
				umAdaptee.setSize(new Dimension(146,50));
				java.util.ArrayList umAdapteeOper = new ArrayList();
				OperationEditableTableItem umAdapteeopi = new OperationEditableTableItem();
				umAdapteeopi.stype = "void";
				umAdapteeopi.name = "SpecificRequest";
				umAdapteeopi.scope = 0;
				umAdapteeOper.add(umAdapteeopi);
				umAdaptee.getClassModel().setOperations(umAdapteeOper);

				for(int i=0; i<item.length;i++){

					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								 
							}
						
						}else{
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							 
						}
					}else if(item[i].getText().equals("Target")){
						if(!item[i].getText(1).equals("Target")){
							FinalClassModel model=modelMatch(umTarget, item[i].getText(1));							
							if(model!=null){
								umTarget=diagramInModel(model,umTarget,n3EditorDiagramModel);
							}else{
								umTarget.setName(item[i].getText(1));
								this.makeTreeModel(umTarget, n3EditorDiagramModel);
								 
							}

						}else{
							this.makeTreeModel(umTarget, n3EditorDiagramModel);
							 
						}
					}else if(item[i].getText().equals("Adapter")){
						if(!item[i].getText(1).equals("Adapter")){
							FinalClassModel model=modelMatch(umAdapter, item[i].getText(1));							
							if(model!=null){
								umAdapter=diagramInModel(model,umAdapter,n3EditorDiagramModel);
							}else{
								umAdapter.setName(item[i].getText(1));
								this.makeTreeModel(umAdapter, n3EditorDiagramModel);
								 
							}
						}else{
							this.makeTreeModel(umAdapter, n3EditorDiagramModel);
							 
							}
					}else if(item[i].getText().equals("Adaptee")){
						if(!item[i].getText(1).equals("Adaptee")){
							FinalClassModel model=modelMatch(umAdaptee, item[i].getText(1));							
							if(model!=null){
								umAdaptee=diagramInModel(model,umAdaptee,n3EditorDiagramModel);
							}else{
								umAdaptee.setName(item[i].getText(1));
								this.makeTreeModel(umAdaptee, n3EditorDiagramModel);
							}
						}else{
							this.makeTreeModel(umAdaptee, n3EditorDiagramModel);
							 
							}
					}
				}				
				
				umClient.setLocation(new Point(484,114));
				umTarget.setLocation(new Point(227,114));
				umAdapter.setLocation(new Point(227,243));
				umAdaptee.setLocation(new Point(23,243));
				
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umTarget);
				n3EditorDiagramModel.addChild(umAdapter);
				n3EditorDiagramModel.addChild(umAdaptee);

				this.createAssociateLineModelModel(umClient, umTarget, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				this.createGeneralizeLineModel(umAdapter, umTarget, n3EditorDiagramModel);
				this.createAssociateLineModelModel(umAdapter, umAdaptee, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=adaptee");
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}
			//BRIDGE
			else if(defineValue==208){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();

				FinalClassModel umAbstraction = new FinalClassModel();
				umAbstraction.setName("Abstraction");
				java.util.ArrayList umAbstractionOper = new ArrayList();
				OperationEditableTableItem opiAbstraction = new OperationEditableTableItem();
				opiAbstraction.stype = "void";
				opiAbstraction.name = "Operation";
				opiAbstraction.scope = 0;
				umAbstractionOper.add(opiAbstraction);
				umAbstraction.getClassModel().setOperations(umAbstractionOper);
				
				FinalClassModel umImplementor = new FinalClassModel();
				umImplementor.setName("Implementor");
				java.util.ArrayList umImplementorOper = new ArrayList();
				OperationEditableTableItem opiImplementor = new OperationEditableTableItem();
				opiAbstraction.stype = "void";
				opiAbstraction.name = "OperationImp";
				opiAbstraction.scope = 0;
				umImplementorOper.add(opiImplementor);
				umImplementor.getClassModel().setOperations(umImplementorOper);
				
				FinalClassModel umRefinedAbstraction = new FinalClassModel();
				umRefinedAbstraction.setName("RefinedAbstraction");
				umRefinedAbstraction.setSize(new Dimension(158,50));
				
				FinalClassModel umConcreteImplementor = new FinalClassModel();
				umConcreteImplementor.setName("ConcreteImplementor");
				umConcreteImplementor.setSize(new Dimension(158,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Abstraction")){
						if(!item[i].getText(1).equals("Abstraction")){
							FinalClassModel model=modelMatch(umAbstraction, item[i].getText(1));							
							if(model!=null){
								umAbstraction=diagramInModel(model,umAbstraction,n3EditorDiagramModel);
							}else{
								umAbstraction.setName(item[i].getText(1));
								this.makeTreeModel(umAbstraction, n3EditorDiagramModel);
								 
							}
						}else{
							umAbstraction.setName(item[i].getText(1));
							this.makeTreeModel(umAbstraction, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Implementor")){
						if(!item[i].getText(1).equals("Implementor")){
							FinalClassModel model=modelMatch(umImplementor, item[i].getText(1));							
							if(model!=null){
								umImplementor=diagramInModel(model,umImplementor,n3EditorDiagramModel);
							}else{
								umImplementor.setName(item[i].getText(1));
								this.makeTreeModel(umImplementor, n3EditorDiagramModel);
								 
							}
						}else{
							umImplementor.setName(item[i].getText(1));
							this.makeTreeModel(umImplementor, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("RefinedAbstraction")){
						if(!item[i].getText(1).equals("RefinedAbstraction")){
							FinalClassModel model=modelMatch(umRefinedAbstraction, item[i].getText(1));							
							if(model!=null){
								umRefinedAbstraction=diagramInModel(model,umRefinedAbstraction,n3EditorDiagramModel);
							}else{
								umRefinedAbstraction.setName(item[i].getText(1));
								this.makeTreeModel(umRefinedAbstraction, n3EditorDiagramModel);
								 
							}
						}else{
							umRefinedAbstraction.setName(item[i].getText(1));
							this.makeTreeModel(umRefinedAbstraction, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().trim().equals("ConcreteImplementor")){
						if(!item[i].getText(1).equals("ConcreteImplementor")){
							FinalClassModel model=modelMatch(umConcreteImplementor, item[i].getText(1));							
							if(model!=null){
								umConcreteImplementor=diagramInModel(model,umConcreteImplementor,n3EditorDiagramModel);
							}else{
								umConcreteImplementor.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteImplementor, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreteImplementor.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteImplementor, n3EditorDiagramModel);
							 
						}			
					}
				}
				
//				this.makeTreeModel(umAbstraction, n3EditorDiagramModel);
//				this.makeTreeModel(umImplementor, n3EditorDiagramModel);
//				this.makeTreeModel(umRefinedAbstraction, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteImplementor, n3EditorDiagramModel);
				
				umAbstraction.setLocation(new Point(47,42));
				umImplementor.setLocation(new Point(311,42));
				umRefinedAbstraction.setLocation(new Point(23,186));
				umConcreteImplementor.setLocation(new Point(287,186));
				
				n3EditorDiagramModel.addChild(umAbstraction);
				n3EditorDiagramModel.addChild(umImplementor);
				n3EditorDiagramModel.addChild(umRefinedAbstraction);
				n3EditorDiagramModel.addChild(umConcreteImplementor);
				
				this.createAssociateLineModelModel(umAbstraction, umImplementor, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=imp,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate");
				this.createGeneralizeLineModel(umRefinedAbstraction, umAbstraction, n3EditorDiagramModel);
				this.createGeneralizeLineModel(umConcreteImplementor, umImplementor,  n3EditorDiagramModel);
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}//BUILDER
			else if(defineValue==209){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umDirector = new FinalClassModel();
				umDirector.setName("Director");
				java.util.ArrayList umDirectorOper = new ArrayList();
				OperationEditableTableItem opiDirector = new OperationEditableTableItem();
				opiDirector.stype = "void";
				opiDirector.name = "Construct";
				opiDirector.scope = 0;
				umDirectorOper.add(opiDirector);
				umDirector.getClassModel().setOperations(umDirectorOper);
				
				FinalClassModel umBuilder = new FinalClassModel();
				umBuilder.setName("Builder");
				java.util.ArrayList umBuilderOper = new ArrayList();
				OperationEditableTableItem opiBuilder = new OperationEditableTableItem();
				opiBuilder.stype = "void";
				opiBuilder.name = "BuildPart";
				opiBuilder.scope = 0;
				umDirectorOper.add(opiBuilder);
				umBuilder.getClassModel().setOperations(umBuilderOper);
				
				FinalClassModel umConcreteBuilder = new FinalClassModel();
				umConcreteBuilder.setName("ConcreteBuilder");
				
				FinalClassModel umProduct = new FinalClassModel();
				umProduct.setName("Product");

				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Director")){
						if(!item[i].getText(1).equals("Director")){
							FinalClassModel model=modelMatch(umDirector, item[i].getText(1));							
							if(model!=null){
								umDirector=diagramInModel(model,umDirector,n3EditorDiagramModel);
							}else{
								umDirector.setName(item[i].getText(1));
								this.makeTreeModel(umDirector, n3EditorDiagramModel);
								 
							}
						}else{
							umDirector.setName(item[i].getText(1));
							this.makeTreeModel(umDirector, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Builder")){
						if(!item[i].getText(1).equals("Builder")){
							FinalClassModel model=modelMatch(umBuilder, item[i].getText(1));							
							if(model!=null){
								umBuilder=diagramInModel(model,umBuilder,n3EditorDiagramModel);
							}else{
								umBuilder.setName(item[i].getText(1));
								this.makeTreeModel(umBuilder, n3EditorDiagramModel);
								 
							}
						}else{
							umBuilder.setName(item[i].getText(1));
							this.makeTreeModel(umBuilder, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("ConcreteBuilder")){
						if(!item[i].getText(1).equals("ConcreteBuilder")){
							FinalClassModel model=modelMatch(umConcreteBuilder, item[i].getText(1));							
							if(model!=null){
								umConcreteBuilder=diagramInModel(model,umConcreteBuilder,n3EditorDiagramModel);
							}else{
								umConcreteBuilder.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteBuilder, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreteBuilder.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteBuilder, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Product")){
						if(!item[i].getText(1).equals("Product")){
							FinalClassModel model=modelMatch(umProduct, item[i].getText(1));							
							if(model!=null){
								umProduct=diagramInModel(model,umProduct,n3EditorDiagramModel);
							}else{
								umProduct.setName(item[i].getText(1));
								this.makeTreeModel(umProduct, n3EditorDiagramModel);
								 
							}
						}else{
							umProduct.setName(item[i].getText(1));
							this.makeTreeModel(umProduct, n3EditorDiagramModel);
							 
						}			
					}
				}
				
//				this.makeTreeModel(umDirector, n3EditorDiagramModel);
//				this.makeTreeModel(umBuilder, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteBuilder, n3EditorDiagramModel);
//				this.makeTreeModel(umProduct, n3EditorDiagramModel);
				
				umDirector.setLocation(new Point(59,66));
				umBuilder.setLocation(new Point(287,66));
				umConcreteBuilder.setLocation(new Point(287,190));
				umProduct.setLocation(new Point(503,190));
				
				n3EditorDiagramModel.addChild(umDirector);
				n3EditorDiagramModel.addChild(umBuilder);
				n3EditorDiagramModel.addChild(umConcreteBuilder);
				n3EditorDiagramModel.addChild(umProduct);
				
				this.createAssociateLineModelModel(umDirector, umBuilder, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=builder,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate");
				this.createGeneralizeLineModel(umConcreteBuilder, umBuilder, n3EditorDiagramModel);
				this.createDependencyLineModel(umConcreteBuilder, umProduct,  n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
				
			}
			//CHAIN_OF_REPONSIBILITY
			else if(defineValue==210){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				FinalClassModel umHandler = new FinalClassModel();
				umHandler.setName("Handler");
				java.util.ArrayList umHandlerOper = new ArrayList();
				OperationEditableTableItem opiHandler = new OperationEditableTableItem();
				opiHandler.stype = "void";
				opiHandler.name = "BuildPart";
				opiHandler.scope = 0;
				umHandlerOper.add(opiHandler);
				umHandler.getClassModel().setOperations(umHandlerOper);				
				
				FinalClassModel umConcreteHandler = new FinalClassModel();
				umConcreteHandler.setName("ConcreteHandler");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								 
							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Handler")){
						if(!item[i].getText(1).equals("Handler")){
							FinalClassModel model=modelMatch(umHandler, item[i].getText(1));							
							if(model!=null){
								umHandler=diagramInModel(model,umHandler,n3EditorDiagramModel);
							}else{
								umHandler.setName(item[i].getText(1));
								this.makeTreeModel(umHandler, n3EditorDiagramModel);
								 
							}
						}else{
							umHandler.setName(item[i].getText(1));
							this.makeTreeModel(umHandler, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("ConcreteHandler")){
						if(!item[i].getText(1).equals("ConcreteHandler")){
							FinalClassModel model=modelMatch(umConcreteHandler, item[i].getText(1));							
							if(model!=null){
								umConcreteHandler=diagramInModel(model,umConcreteHandler,n3EditorDiagramModel);
							}else{
								umConcreteHandler.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteHandler, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreteHandler.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteHandler, n3EditorDiagramModel);
							 
						}			
					}
				}
				
//				this.makeTreeModel(umClient, n3EditorDiagramModel);
//				this.makeTreeModel(umHandler, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteHandler, n3EditorDiagramModel);
				
				umClient.setLocation(new Point(35,54));
				umHandler.setLocation(new Point(263,54));
				umConcreteHandler.setLocation(new Point(263,227));
				
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umHandler);
				n3EditorDiagramModel.addChild(umConcreteHandler);
				
				this.createAssociateLineModelModel(umClient, umHandler, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				this.createAssociateLineModelModel(umHandler, umHandler, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=successor");
				this.createGeneralizeLineModel(umConcreteHandler, umHandler, n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}
			else if(defineValue==211){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				FinalClassModel umInvoker = new FinalClassModel();
				umInvoker.setName("Invoker");
				
				FinalClassModel umCommand = new FinalClassModel();
				umCommand.setName("Command");
				java.util.ArrayList umCommandOper = new ArrayList();
				OperationEditableTableItem opiCommand = new OperationEditableTableItem();
				opiCommand.stype = "void";
				opiCommand.name = "Execute";
				opiCommand.scope = 0;
				umCommandOper.add(opiCommand);
				umCommand.getClassModel().setOperations(umCommandOper);
				
				FinalClassModel umReceiver = new FinalClassModel();
				umReceiver.setName("Receiver");
				java.util.ArrayList umReceiverOper = new ArrayList();
				OperationEditableTableItem opiReceiver = new OperationEditableTableItem();
				opiReceiver.stype = "void";
				opiReceiver.name = "Action";
				opiReceiver.scope = 0;
				umReceiverOper.add(opiReceiver);
				umReceiver.getClassModel().setOperations(umReceiverOper);
				
				FinalClassModel umConcreteCommand = new FinalClassModel();
				umConcreteCommand.setName("ConcreteCommand");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								 
							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Invoker")){
						if(!item[i].getText(1).equals("Invoker")){
							FinalClassModel model=modelMatch(umInvoker, item[i].getText(1));							
							if(model!=null){
								umInvoker=diagramInModel(model,umInvoker,n3EditorDiagramModel);
							}else{
								umInvoker.setName(item[i].getText(1));
								this.makeTreeModel(umInvoker, n3EditorDiagramModel);
								 
							}
						}else{
							umInvoker.setName(item[i].getText(1));
							this.makeTreeModel(umInvoker, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Command")){
						if(!item[i].getText(1).equals("Command")){
							FinalClassModel model=modelMatch(umCommand, item[i].getText(1));							
							if(model!=null){
								umCommand=diagramInModel(model,umCommand,n3EditorDiagramModel);
							}else{
								umCommand.setName(item[i].getText(1));
								this.makeTreeModel(umCommand, n3EditorDiagramModel);
								 
							}
						}else{
							umCommand.setName(item[i].getText(1));
							this.makeTreeModel(umCommand, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Receiver")){
						if(!item[i].getText(1).equals("Receiver")){
							FinalClassModel model=modelMatch(umReceiver, item[i].getText(1));							
							if(model!=null){
								umReceiver=diagramInModel(model,umReceiver,n3EditorDiagramModel);
							}else{
								umReceiver.setName(item[i].getText(1));
								this.makeTreeModel(umReceiver, n3EditorDiagramModel);
								 
							}
						}else{
							umReceiver.setName(item[i].getText(1));
							this.makeTreeModel(umReceiver, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("ConcreteCommand")){
						if(!item[i].getText(1).equals("ConcreteCommand")){
							FinalClassModel model=modelMatch(umConcreteCommand, item[i].getText(1));							
							if(model!=null){
								umConcreteCommand=diagramInModel(model,umConcreteCommand,n3EditorDiagramModel);
							}else{
								umConcreteCommand.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteCommand, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreteCommand.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteCommand, n3EditorDiagramModel);
							 
						}			
					}
				}
				
				
//				this.makeTreeModel(umClient, n3EditorDiagramModel);
//				this.makeTreeModel(umInvoker, n3EditorDiagramModel);
//				this.makeTreeModel(umCommand, n3EditorDiagramModel);
//				this.makeTreeModel(umReceiver, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteCommand, n3EditorDiagramModel);
				
				umClient.setLocation(new Point(11,66));
				umInvoker.setLocation(new Point(191,66));
				umCommand.setLocation(new Point(383,66));
				umReceiver.setLocation(new Point(191,151));
				umConcreteCommand.setLocation(new Point(383,227));
				
				
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umInvoker);
				n3EditorDiagramModel.addChild(umCommand);
				n3EditorDiagramModel.addChild(umReceiver);
				n3EditorDiagramModel.addChild(umConcreteCommand);
				
				this.createAssociateLineModelModel(umClient, umReceiver, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+"Bendpoint=(0.5:-1:84:-181:-1)");
				this.createAssociateLineModelModel(umConcreteCommand, umReceiver, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_ROLE+"=receiver"+","+"Bendpoint=(0.5:0:-76:192:0)");
				this.createAssociateLineModelModel(umInvoker, umCommand, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate");
				this.createGeneralizeLineModel(umConcreteCommand, umCommand, n3EditorDiagramModel);
				this.createDependencyLineModel(umClient, umConcreteCommand, n3EditorDiagramModel,"Bendpoint=(0.5:-1:161:-373:0)");
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());

			}
			else if(defineValue==212){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");

				FinalClassModel umComponent = new FinalClassModel();
				umComponent.setName("Component");
				java.util.ArrayList umComponentOper = new ArrayList();
				java.util.ArrayList umComponentPar = new ArrayList();
				OperationEditableTableItem opiComponent = new OperationEditableTableItem();
				ParameterEditableTableItem parComponent = new ParameterEditableTableItem();
				opiComponent.stype = "void";
				opiComponent.name = "Operation";
				opiComponent.scope = 0;
				umComponentOper.add(opiComponent);
				umComponentPar = new ArrayList();
				
				umComponentPar = new ArrayList();
				opiComponent = new OperationEditableTableItem();
				parComponent = new ParameterEditableTableItem();
				opiComponent.stype = "void";
				opiComponent.name = "Add";
				opiComponent.scope = 0;
				parComponent.name="Parameter1";
				parComponent.stype="Component";
				parComponent.defalut="";
				umComponentPar.add(parComponent);
				opiComponent.setParams(umComponentPar);
				umComponentOper.add(opiComponent);
			
				umComponentPar = new ArrayList();
				opiComponent = new OperationEditableTableItem();
				parComponent = new ParameterEditableTableItem();
				opiComponent.stype = "void";
				opiComponent.name = "Remove";
				opiComponent.scope = 0;
				parComponent.name="Parameter1";
				parComponent.stype="Component";
				parComponent.defalut="";
				umComponentPar.add(parComponent);
				opiComponent.setParams(umComponentPar);
				umComponentOper.add(opiComponent);

				umComponentPar = new ArrayList();
				opiComponent = new OperationEditableTableItem();
				parComponent = new ParameterEditableTableItem();
				opiComponent.stype = "void";
				opiComponent.name = "GetChild";
				opiComponent.scope = 0;
				parComponent.name="a";
				parComponent.stype="int";
				parComponent.defalut="";
				umComponentPar.add(parComponent);
				opiComponent.setParams(umComponentPar);
				umComponentOper.add(opiComponent);
				
				umComponent.getClassModel().setOperations(umComponentOper);
				
				FinalClassModel umLeaf = new FinalClassModel();
				umLeaf.setName("Leaf");
				
				FinalClassModel umComposite = new FinalClassModel();
				umComposite.setName("Composite");
				
				umComponent.setSize(new Dimension(270,108));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								 
							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Component")){
						if(!item[i].getText(1).equals("Component")){
							FinalClassModel model=modelMatch(umComponent, item[i].getText(1));							
							if(model!=null){
								umComponent=diagramInModel(model,umComponent,n3EditorDiagramModel);
							}else{
								umComponent.setName(item[i].getText(1));
								this.makeTreeModel(umComponent, n3EditorDiagramModel);
								 
							}
						}else{
							umComponent.setName(item[i].getText(1));
							this.makeTreeModel(umComponent, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Leaf")){
						if(!item[i].getText(1).equals("Leaf")){
							FinalClassModel model=modelMatch(umLeaf, item[i].getText(1));							
							if(model!=null){
								umLeaf=diagramInModel(model,umLeaf,n3EditorDiagramModel);
							}else{
								umLeaf.setName(item[i].getText(1));
								this.makeTreeModel(umLeaf, n3EditorDiagramModel);
								 
							}
						}else{
							umLeaf.setName(item[i].getText(1));
							this.makeTreeModel(umLeaf, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Composite")){
						if(!item[i].getText(1).equals("Composite")){
							FinalClassModel model=modelMatch(umComposite, item[i].getText(1));							
							if(model!=null){
								umComposite=diagramInModel(model,umComposite,n3EditorDiagramModel);
							}else{
								umComposite.setName(item[i].getText(1));
								this.makeTreeModel(umComposite, n3EditorDiagramModel);
								 
							}
						}else{
							umComposite.setName(item[i].getText(1));
							this.makeTreeModel(umComposite, n3EditorDiagramModel);
							 
						}			
					}
				}
//				this.makeTreeModel(umClient, n3EditorDiagramModel);
//				this.makeTreeModel(umComponent, n3EditorDiagramModel);
//				this.makeTreeModel(umLeaf, n3EditorDiagramModel);
//				this.makeTreeModel(umComposite, n3EditorDiagramModel);

				umClient.setLocation(new Point(75,78));
				umComponent.setLocation(new Point(263,47));
				umLeaf.setLocation(new Point(203,258));
				umComposite.setLocation(new Point(491,258));
								
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umComponent);
				n3EditorDiagramModel.addChild(umLeaf);
				n3EditorDiagramModel.addChild(umComposite);
				
				this.createAssociateLineModelModel(umClient, umComponent, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,");
				this.createAssociateLineModelModel(umComposite, umComponent, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_MUL+"=*,"+"Bendpoint=(0.5:-1:-182:147:0)");
				this.createGeneralizeLineModel(umLeaf, umComponent, n3EditorDiagramModel,"Bendpoint=(0.5:0:-84:-139:98)"+","+"Bendpoint=(0.5:140:-84:0:98)");
				this.createGeneralizeLineModel(umComposite,umComponent , n3EditorDiagramModel,"Bendpoint=(0.5:-1:-84:148:98)"+","+"Bendpoint=(0.5:-149:-84:0:98)");
//				this.createAssociateLineModelModel(umInvoker, umCommand, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate");
//				this.createGeneralizeLineModel(umConcreteCommand, umCommand, n3EditorDiagramModel);
//				this.createDependencyLineModel(umClient, umConcreteCommand, n3EditorDiagramModel,"Bendpoint=(0.5:-1:161:-373:0)");
			
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());

			}else if(defineValue==213){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umComponent = new FinalClassModel();
				umComponent.setName("Component");
				java.util.ArrayList umComponentOper = new ArrayList();
				java.util.ArrayList umComponentPar = new ArrayList();
				OperationEditableTableItem opiComponent = new OperationEditableTableItem();
				ParameterEditableTableItem parComponent = new ParameterEditableTableItem();
				opiComponent.stype = "void";
				opiComponent.name = "Operation";
				opiComponent.scope = 0;
				umComponentOper.add(opiComponent);
				umComponent.getClassModel().setOperations(umComponentOper);
				
				FinalClassModel umConcreateComponent = new FinalClassModel();
				umConcreateComponent.setName("ConcreateComponent");
				
				FinalClassModel umDecorator = new FinalClassModel();
				umDecorator.setName("Decorator");
				
				FinalClassModel umConcreteDecorator = new FinalClassModel();
				umConcreteDecorator.setName("ConcreteDecorator");
				
				umConcreateComponent.setSize(new Dimension(134,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Component")){
						if(!item[i].getText(1).equals("Component")){
							FinalClassModel model=modelMatch(umComponent, item[i].getText(1));							
							if(model!=null){
								umComponent=diagramInModel(model,umComponent,n3EditorDiagramModel);
							}else{
								umComponent.setName(item[i].getText(1));
								this.makeTreeModel(umComponent, n3EditorDiagramModel);
								 
							}
						}else{
							umComponent.setName(item[i].getText(1));
							this.makeTreeModel(umComponent, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("ConcreateComponent")){
						if(!item[i].getText(1).equals("ConcreateComponent")){
							FinalClassModel model=modelMatch(umConcreateComponent, item[i].getText(1));							
							if(model!=null){
								umConcreateComponent=diagramInModel(model,umConcreateComponent,n3EditorDiagramModel);
							}else{
								umConcreateComponent.setName(item[i].getText(1));
								this.makeTreeModel(umConcreateComponent, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreateComponent.setName(item[i].getText(1));
							this.makeTreeModel(umConcreateComponent, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Decorator")){
						if(!item[i].getText(1).equals("Decorator")){
							FinalClassModel model=modelMatch(umDecorator, item[i].getText(1));							
							if(model!=null){
								umDecorator=diagramInModel(model,umDecorator,n3EditorDiagramModel);
							}else{
								umDecorator.setName(item[i].getText(1));
								this.makeTreeModel(umDecorator, n3EditorDiagramModel);
								
							}
						}else{
							umDecorator.setName(item[i].getText(1));
							this.makeTreeModel(umDecorator, n3EditorDiagramModel);
						
						}			
					}else if(item[i].getText().equals("ConcreteDecorator")){
						if(!item[i].getText(1).equals("ConcreteDecorator")){
							FinalClassModel model=modelMatch(umConcreteDecorator, item[i].getText(1));							
							if(model!=null){
								umConcreteDecorator=diagramInModel(model,umConcreteDecorator,n3EditorDiagramModel);
							}else{
								umConcreteDecorator.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteDecorator, n3EditorDiagramModel);
								
							}
						}else{
							umConcreteDecorator.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteDecorator, n3EditorDiagramModel);
						
						}			
					}
				}
				
				
				
//				this.makeTreeModel(umComponent, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreateComponent, n3EditorDiagramModel);
//				this.makeTreeModel(umDecorator, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteDecorator, n3EditorDiagramModel);
				
				umComponent.setLocation(new Point(179,39));
				umConcreateComponent.setLocation(new Point(59,174));
				umDecorator.setLocation(new Point(299,174));
				umConcreteDecorator.setLocation(new Point(299,306));
							
				n3EditorDiagramModel.addChild(umComponent);
				n3EditorDiagramModel.addChild(umConcreateComponent);
				n3EditorDiagramModel.addChild(umDecorator);
				n3EditorDiagramModel.addChild(umConcreteDecorator);
				
				this.createAssociateLineModelModel(umDecorator, umComponent, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_ROLE+"=component,"+"Bendpoint=(0.5:0:-136:120:-1)");
				this.createGeneralizeLineModel(umDecorator,umComponent , n3EditorDiagramModel,"Bendpoint=(0.5:0:-75:120:60)"+","+"Bendpoint=(0.5:-120:-75:0:60)");
				this.createGeneralizeLineModel(umConcreateComponent,umComponent , n3EditorDiagramModel,"Bendpoint=(0.5:0:-75:-109:60)"+","+"Bendpoint=(0.5:109:-75:0:60)");
				this.createGeneralizeLineModel(umConcreteDecorator,umDecorator , n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==214){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umFacade = new FinalClassModel();
				umFacade.setName("Facade");
				
				FinalClassModel umSubsystemClasses = new FinalClassModel();
				umSubsystemClasses.setName("Subsystem Classes");
				
				umSubsystemClasses.setSize(new Dimension(134,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Facade")){
						if(!item[i].getText(1).equals("Facade")){
							FinalClassModel model=modelMatch(umFacade, item[i].getText(1));							
							if(model!=null){
								umFacade=diagramInModel(model,umFacade,n3EditorDiagramModel);
							}else{
								umFacade.setName(item[i].getText(1));
								this.makeTreeModel(umFacade, n3EditorDiagramModel);
							
							}
						}else{
							umFacade.setName(item[i].getText(1));
							this.makeTreeModel(umFacade, n3EditorDiagramModel);
						
						}			
					}else if(item[i].getText().equals("Subsystem Classes")){
						if(!item[i].getText(1).equals("Subsystem Classes")){
							FinalClassModel model=modelMatch(umSubsystemClasses, item[i].getText(1));							
							if(model!=null){
								umSubsystemClasses=diagramInModel(model,umSubsystemClasses,n3EditorDiagramModel);
							}else{
								umSubsystemClasses.setName(item[i].getText(1));
								this.makeTreeModel(umSubsystemClasses, n3EditorDiagramModel);
								
							}
						}else{
							umSubsystemClasses.setName(item[i].getText(1));
							this.makeTreeModel(umSubsystemClasses, n3EditorDiagramModel);
						
						}			
					}
				}
				
//				this.makeTreeModel(umFacade, n3EditorDiagramModel);
//				this.makeTreeModel(umSubsystemClasses, n3EditorDiagramModel);
				
				umFacade.setLocation(new Point(59,126));
				umSubsystemClasses.setLocation(new Point(47,267));
				
				
				
				n3EditorDiagramModel.addChild(umFacade);
				n3EditorDiagramModel.addChild(umSubsystemClasses);
				
				this.createAssociateLineModelModel(umFacade, umSubsystemClasses, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==215){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umProduct = new FinalClassModel();
				umProduct.setName("Product");
				
				FinalClassModel umCreator = new FinalClassModel();
				umCreator.setName("Creator");
				java.util.ArrayList umCreatorOper = new ArrayList();
				OperationEditableTableItem opiCreator = new OperationEditableTableItem();
				opiCreator.stype = "void";
				opiCreator.name = "FactoryMethod";
				opiCreator.scope = 0;
				umCreatorOper.add(opiCreator);
				umCreator.getClassModel().setOperations(umCreatorOper);
				
				FinalClassModel umConcreteProduct = new FinalClassModel();
				umConcreteProduct.setName("ConcreteProduct");				
				
				FinalClassModel umConcreteCreator = new FinalClassModel();
				umConcreteCreator.setName("ConcreteCreator");
				
				umCreator.setSize(new Dimension(134,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Product")){
						if(!item[i].getText(1).equals("Product")){
							FinalClassModel model=modelMatch(umProduct, item[i].getText(1));							
							if(model!=null){
								umProduct=diagramInModel(model,umProduct,n3EditorDiagramModel);
							}else{
								umProduct.setName(item[i].getText(1));
								this.makeTreeModel(umProduct, n3EditorDiagramModel);
								
							}
						}else{
							umProduct.setName(item[i].getText(1));
							this.makeTreeModel(umProduct, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("Creator")){
						if(!item[i].getText(1).equals("Creator")){
							FinalClassModel model=modelMatch(umCreator, item[i].getText(1));							
							if(model!=null){
								umCreator=diagramInModel(model,umCreator,n3EditorDiagramModel);
							}else{
								umCreator.setName(item[i].getText(1));
								this.makeTreeModel(umCreator, n3EditorDiagramModel);
								
							}
						}else{
							umCreator.setName(item[i].getText(1));
							this.makeTreeModel(umCreator, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("ConcreteProduct")){
						if(!item[i].getText(1).equals("ConcreteProduct")){
							FinalClassModel model=modelMatch(umConcreteProduct, item[i].getText(1));							
							if(model!=null){
								umConcreteProduct=diagramInModel(model,umConcreteProduct,n3EditorDiagramModel);
							}else{
								umConcreteProduct.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteProduct, n3EditorDiagramModel);
								
							}
						}else{
							umConcreteProduct.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteProduct, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("ConcreteCreator")){
						if(!item[i].getText(1).equals("ConcreteCreator")){
							FinalClassModel model=modelMatch(umConcreteCreator, item[i].getText(1));							
							if(model!=null){
								umConcreteCreator=diagramInModel(model,umConcreteCreator,n3EditorDiagramModel);
							}else{
								umConcreteCreator.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteCreator, n3EditorDiagramModel);
								
							}
						}else{
							umConcreteCreator.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteCreator, n3EditorDiagramModel);
							
						}			
					}
				}
				
				
//				this.makeTreeModel(umProduct, n3EditorDiagramModel);
//				this.makeTreeModel(umCreator, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteProduct, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteCreator, n3EditorDiagramModel);
				
				umProduct.setLocation(new Point(35,102));
				umCreator.setLocation(new Point(239,102));
				umConcreteProduct.setLocation(new Point(35,279));
				umConcreteCreator.setLocation(new Point(251,279));
				
				
				
				n3EditorDiagramModel.addChild(umProduct);
				n3EditorDiagramModel.addChild(umCreator);
				n3EditorDiagramModel.addChild(umConcreteProduct);
				n3EditorDiagramModel.addChild(umConcreteCreator);
				
				this.createDependencyLineModel(umConcreteCreator, umConcreteProduct, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				this.createGeneralizeLineModel(umConcreteCreator,umCreator , n3EditorDiagramModel);
				this.createGeneralizeLineModel(umConcreteProduct,umProduct , n3EditorDiagramModel);
				
//				this.createAssociateLineModelModel(umFacade, umSubsystemClasses, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==216){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umFlyweightFactory = new FinalClassModel();
				umFlyweightFactory.setName("FlyweightFactory");			
				java.util.ArrayList umFlyweightFactoryOper = new ArrayList();
				OperationEditableTableItem opiFlyweightFactory = new OperationEditableTableItem();
				opiFlyweightFactory.stype = "void";
				opiFlyweightFactory.name = "GetFlyweight";
				opiFlyweightFactory.scope = 0;
				umFlyweightFactoryOper.add(opiFlyweightFactory);
				opiFlyweightFactory = new OperationEditableTableItem();
				opiFlyweightFactory.stype = "void";
				opiFlyweightFactory.name = "Operation1";
				opiFlyweightFactory.scope = 0;
				umFlyweightFactoryOper.add(opiFlyweightFactory);
				umFlyweightFactory.getClassModel().setOperations(umFlyweightFactoryOper);
				
				
				FinalClassModel umFlyweight = new FinalClassModel();
				umFlyweight.setName("Flyweight");
				java.util.ArrayList umFlyweightOper = new ArrayList();
				OperationEditableTableItem opiFlyweight = new OperationEditableTableItem();
				opiFlyweight.stype = "void";
				opiFlyweight.name = "Operation";
				opiFlyweight.scope = 0;
				umFlyweightOper.add(opiFlyweight);
				umFlyweight.getClassModel().setOperations(umFlyweightOper);
				
				FinalClassModel umConcreteFlyweight = new FinalClassModel();
				umConcreteFlyweight.setName("ConcreteFlyweight");
				
				
				FinalClassModel umunsharedConcreteFlyweight = new FinalClassModel();
				umunsharedConcreteFlyweight.setName("unsharedConcreteFlyweight");

				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				umunsharedConcreteFlyweight.setSize(new Dimension(169,50));
				umFlyweightFactory.setSize(new Dimension(150,72));
				
//				this.createAssociateLineModelModel(umFacade, umSubsystemClasses, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("FlyweightFactory")){
						if(!item[i].getText(1).equals("FlyweightFactory")){
							FinalClassModel model=modelMatch(umFlyweightFactory, item[i].getText(1));							
							if(model!=null){
								umFlyweightFactory=diagramInModel(model,umFlyweightFactory,n3EditorDiagramModel);
							}else{
								umFlyweightFactory.setName(item[i].getText(1));
								this.makeTreeModel(umFlyweightFactory, n3EditorDiagramModel);
								
							}
						}else{
							umFlyweightFactory.setName(item[i].getText(1));
							this.makeTreeModel(umFlyweightFactory, n3EditorDiagramModel);
							 
						}			
					}else if(item[i].getText().equals("Flyweight")){
						if(!item[i].getText(1).equals("Flyweight")){
							FinalClassModel model=modelMatch(umFlyweight, item[i].getText(1));							
							if(model!=null){
								umFlyweight=diagramInModel(model,umFlyweight,n3EditorDiagramModel);
							}else{
								umFlyweight.setName(item[i].getText(1));
								this.makeTreeModel(umFlyweight, n3EditorDiagramModel);
								
							}
						}else{
							umFlyweight.setName(item[i].getText(1));
							this.makeTreeModel(umFlyweight, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("ConcreteFlyweight")){
						if(!item[i].getText(1).equals("ConcreteFlyweight")){
							FinalClassModel model=modelMatch(umConcreteFlyweight, item[i].getText(1));							
							if(model!=null){
								umConcreteFlyweight=diagramInModel(model,umConcreteFlyweight,n3EditorDiagramModel);
							}else{
								umConcreteFlyweight.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteFlyweight, n3EditorDiagramModel);
								 
							}
						}else{
							umConcreteFlyweight.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteFlyweight, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("UnsharedConcreteFlyweight")){
						if(!item[i].getText(1).equals("UnsharedConcreteFlyweight")){
							FinalClassModel model=modelMatch(umunsharedConcreteFlyweight, item[i].getText(1));							
							if(model!=null){
								umunsharedConcreteFlyweight=diagramInModel(model,umunsharedConcreteFlyweight,n3EditorDiagramModel);
							}else{
								umunsharedConcreteFlyweight.setName(item[i].getText(1));
								this.makeTreeModel(umunsharedConcreteFlyweight, n3EditorDiagramModel);
								
							}
						}else{
							umunsharedConcreteFlyweight.setName(item[i].getText(1));
							this.makeTreeModel(umunsharedConcreteFlyweight, n3EditorDiagramModel);
						
						}			
					}else if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								
							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							
						}			
					}
				}
				
//				this.makeTreeModel(umFlyweightFactory, n3EditorDiagramModel);
//				this.makeTreeModel(umFlyweight, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteFlyweight, n3EditorDiagramModel);
//				this.makeTreeModel(umunsharedConcreteFlyweight, n3EditorDiagramModel);
//				this.makeTreeModel(umClient, n3EditorDiagramModel);
				
				umFlyweightFactory.setLocation(new Point(3,24));
				umFlyweight.setLocation(new Point(350,34));
				umConcreteFlyweight.setLocation(new Point(237,201));
				umunsharedConcreteFlyweight.setLocation(new Point(459,201));
				umClient.setLocation(new Point(21,265));
				

				
				n3EditorDiagramModel.addChild(umFlyweightFactory);
				n3EditorDiagramModel.addChild(umFlyweight);
				n3EditorDiagramModel.addChild(umConcreteFlyweight);
				n3EditorDiagramModel.addChild(umunsharedConcreteFlyweight);
				n3EditorDiagramModel.addChild(umClient);
				
				this.createAssociateLineModelModel(umFlyweightFactory, umFlyweight, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_ROLE+"=flyweights,"+LineModel.ID_TARGET_MUL+"=*");
				this.createAssociateLineModelModel(umClient, umFlyweightFactory, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				this.createAssociateLineModelModel(umClient, umunsharedConcreteFlyweight, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+"Bendpoint=(0.5:335:0:-131:64)"+","+"Bendpoint=(0.5:335:-65:-131:-1)");
				this.createAssociateLineModelModel(umClient, umConcreteFlyweight, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+"Bendpoint=(0.5:107:0:-109:64)"+","+"Bendpoint=(0.5:107:-64:-109:0)");
				this.createGeneralizeLineModel(umunsharedConcreteFlyweight,umFlyweight , n3EditorDiagramModel,"Bendpoint=(0.5:0:-78:137:89)"+","+"Bendpoint=(0.5:-139:-78:-2:89)");
				this.createGeneralizeLineModel(umConcreteFlyweight,umFlyweight , n3EditorDiagramModel,"Bendpoint=(0.5:-1:-78:-114:89)"+","+"Bendpoint=(0.5:112:-78:-1:89)");
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==217){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				FinalClassModel umContext = new FinalClassModel();
				umContext.setName("Context");
				
				FinalClassModel umAbstractExpression = new FinalClassModel();
				umAbstractExpression.setName("AbstractExpression");
				java.util.ArrayList umAbstractExpressionOper = new ArrayList();
				OperationEditableTableItem opiAbstractExpression = new OperationEditableTableItem();
				opiAbstractExpression.stype = "void";
				opiAbstractExpression.name = "Interpret";
				opiAbstractExpression.scope = 0;
				umAbstractExpressionOper.add(opiAbstractExpression);
				umAbstractExpression.getClassModel().setOperations(umAbstractExpressionOper);
				
				FinalClassModel umTerminalExpression = new FinalClassModel();
				umTerminalExpression.setName("TerminalExpression");
				
				FinalClassModel umNonterminalExpression = new FinalClassModel();
				umNonterminalExpression.setName("NonterminalExpression");
				
				umTerminalExpression.setSize(new Dimension(135,50));
				umNonterminalExpression.setSize(new Dimension(135,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);
								 
							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("Context")){
						if(!item[i].getText(1).equals("Context")){
							FinalClassModel model=modelMatch(umContext, item[i].getText(1));							
							if(model!=null){
								umContext=diagramInModel(model,umContext,n3EditorDiagramModel);
							}else{
								umContext.setName(item[i].getText(1));
								this.makeTreeModel(umContext, n3EditorDiagramModel);
								
							}
						}else{
							umContext.setName(item[i].getText(1));
							this.makeTreeModel(umContext, n3EditorDiagramModel);
						
						}			
					}else if(item[i].getText().equals("AbstractExpression")){
						if(!item[i].getText(1).equals("AbstractExpression")){
							FinalClassModel model=modelMatch(umAbstractExpression, item[i].getText(1));							
							if(model!=null){
								umAbstractExpression=diagramInModel(model,umAbstractExpression,n3EditorDiagramModel);
							}else{
								umAbstractExpression.setName(item[i].getText(1));
								this.makeTreeModel(umAbstractExpression, n3EditorDiagramModel);
								
							}
						}else{
							umAbstractExpression.setName(item[i].getText(1));
							this.makeTreeModel(umAbstractExpression, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("TerminalExpression")){
						if(!item[i].getText(1).equals("TerminalExpression")){
							FinalClassModel model=modelMatch(umTerminalExpression, item[i].getText(1));							
							if(model!=null){
								umTerminalExpression=diagramInModel(model,umTerminalExpression,n3EditorDiagramModel);
							}else{
								umTerminalExpression.setName(item[i].getText(1));
								this.makeTreeModel(umTerminalExpression, n3EditorDiagramModel);
								
							}
						}else{
							umTerminalExpression.setName(item[i].getText(1));
							this.makeTreeModel(umTerminalExpression, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("NonterminalExpression")){
						if(!item[i].getText(1).equals("NonterminalExpression")){
							FinalClassModel model=modelMatch(umNonterminalExpression, item[i].getText(1));							
							if(model!=null){
								umNonterminalExpression=diagramInModel(model,umNonterminalExpression,n3EditorDiagramModel);
							}else{
								umNonterminalExpression.setName(item[i].getText(1));
								this.makeTreeModel(umNonterminalExpression, n3EditorDiagramModel);
								
							}
						}else{
							umNonterminalExpression.setName(item[i].getText(1));
							this.makeTreeModel(umNonterminalExpression, n3EditorDiagramModel);
							
						}			
					}
				}
				
//				this.makeTreeModel(umClient, n3EditorDiagramModel);
//				this.makeTreeModel(umContext, n3EditorDiagramModel);
//				this.makeTreeModel(umAbstractExpression, n3EditorDiagramModel);
//				this.makeTreeModel(umTerminalExpression, n3EditorDiagramModel);
//				this.makeTreeModel(umNonterminalExpression, n3EditorDiagramModel);
				
				umClient.setLocation(new Point(53,143));
				umContext.setLocation(new Point(261,27));
				umAbstractExpression.setLocation(new Point(261,143));
				umTerminalExpression.setLocation(new Point(127,255));
				umNonterminalExpression.setLocation(new Point(372,255));
				

				
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umContext);
				n3EditorDiagramModel.addChild(umAbstractExpression);
				n3EditorDiagramModel.addChild(umTerminalExpression);
				n3EditorDiagramModel.addChild(umNonterminalExpression);
				
				this.createAssociateLineModelModel(umClient, umContext, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+"Bendpoint=(0.5:82:0:-126:116)"+","+"Bendpoint=(0.5:82:-117:-126:-1)");
				this.createAssociateLineModelModel(umClient, umAbstractExpression, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,");
				this.createAssociateLineModelModel(umNonterminalExpression, umAbstractExpression, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_MUL+"=*,"+"Bendpoint=(0.5:-1:-112:121:0)");
				this.createGeneralizeLineModel(umTerminalExpression,umAbstractExpression , n3EditorDiagramModel,"Bendpoint=(0.5:1:-53:-122:59)"+","+"Bendpoint=(0.5:123:-53:0:59)");
				this.createGeneralizeLineModel(umNonterminalExpression,umAbstractExpression , n3EditorDiagramModel,"Bendpoint=(0.5:0:-53:122:59)"+","+"Bendpoint=(0.5:-122:-53:0:59)");
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			
			}else if(defineValue==218){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umAggregate = new FinalClassModel();
				umAggregate.setName("Aggregate");
				java.util.ArrayList umAggregateOper = new ArrayList();
				OperationEditableTableItem opiAggregate = new OperationEditableTableItem();
				opiAggregate.stype = "void";
				opiAggregate.name = "CreateIterator";
				opiAggregate.scope = 0;
				umAggregateOper.add(opiAggregate);
				umAggregate.getClassModel().setOperations(umAggregateOper);
				
				FinalClassModel umIterator = new FinalClassModel();
				umIterator.setName("Iterator");
				java.util.ArrayList umIteratorOper = new ArrayList();
				OperationEditableTableItem opiIterator= new OperationEditableTableItem();
				opiIterator.stype = "void";
				opiIterator.name = "First";
				opiIterator.scope = 0;
				umIteratorOper.add(opiIterator);
				opiIterator= new OperationEditableTableItem();
				opiIterator.stype = "void";
				opiIterator.name = "Next";
				opiIterator.scope = 0;
				umIteratorOper.add(opiIterator);
				opiIterator= new OperationEditableTableItem();
				opiIterator.stype = "void";
				opiIterator.name = "IsDone";
				opiIterator.scope = 0;
				umIteratorOper.add(opiIterator);
				opiIterator= new OperationEditableTableItem();
				opiIterator.stype = "void";
				opiIterator.name = "CurrentItem";
				opiIterator.scope = 0;
				umIteratorOper.add(opiIterator);
				umIterator.getClassModel().setOperations(umIteratorOper);
				
				FinalClassModel umConcreteAggregate= new FinalClassModel();
				umConcreteAggregate.setName("ConcreteAggregate");
				
				FinalClassModel umConcreteIterator= new FinalClassModel();
				umConcreteIterator.setName("ConcreteIterator");
				
				umIterator.setSize(new Dimension(118,100));
				umAggregate.setSize(new Dimension(129,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Aggregate")){
						if(!item[i].getText(1).equals("Aggregate")){
							FinalClassModel model=modelMatch(umAggregate, item[i].getText(1));							
							if(model!=null){
								umAggregate=diagramInModel(model,umAggregate,n3EditorDiagramModel);
							}else{
								umAggregate.setName(item[i].getText(1));
								this.makeTreeModel(umAggregate, n3EditorDiagramModel);
								
							}
						}else{
							umAggregate.setName(item[i].getText(1));
							this.makeTreeModel(umAggregate, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("Iterator")){
						if(!item[i].getText(1).equals("Iterator")){
							FinalClassModel model=modelMatch(umIterator, item[i].getText(1));							
							if(model!=null){
								umIterator=diagramInModel(model,umIterator,n3EditorDiagramModel);
							}else{
								umIterator.setName(item[i].getText(1));
								this.makeTreeModel(umIterator, n3EditorDiagramModel);
								
							}
						}else{
							umIterator.setName(item[i].getText(1));
							this.makeTreeModel(umIterator, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("ConcreteAggregate")){
						if(!item[i].getText(1).equals("ConcreteAggregate")){
							FinalClassModel model=modelMatch(umConcreteAggregate, item[i].getText(1));							
							if(model!=null){
								umConcreteAggregate=diagramInModel(model,umConcreteAggregate,n3EditorDiagramModel);
							}else{
								umConcreteAggregate.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteAggregate, n3EditorDiagramModel);
								
							}
						}else{
							umConcreteAggregate.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteAggregate, n3EditorDiagramModel);
							
						}			
					}else if(item[i].getText().equals("ConcreteIterator")){
						if(!item[i].getText(1).equals("ConcreteIterator")){
							FinalClassModel model=modelMatch(umConcreteIterator, item[i].getText(1));							
							if(model!=null){
								umConcreteIterator=diagramInModel(model,umConcreteIterator,n3EditorDiagramModel);
							}else{
								umConcreteIterator.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteIterator, n3EditorDiagramModel);

							}
						}else{
							umConcreteIterator.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteIterator, n3EditorDiagramModel);

						}			
					}
				}

//				this.makeTreeModel(umAggregate, n3EditorDiagramModel);
//				this.makeTreeModel(umIterator, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteAggregate, n3EditorDiagramModel);
//				this.makeTreeModel(umConcreteIterator, n3EditorDiagramModel);
				
				umAggregate.setLocation(new Point(15,34));
				umIterator.setLocation(new Point(253,34));
				umConcreteAggregate.setLocation(new Point(23,203));
				umConcreteIterator.setLocation(new Point(256,203));
				
			
				n3EditorDiagramModel.addChild(umAggregate);
				n3EditorDiagramModel.addChild(umIterator);
				n3EditorDiagramModel.addChild(umConcreteAggregate);
				n3EditorDiagramModel.addChild(umConcreteIterator);
				
				this.createGeneralizeLineModel(umConcreteIterator,umIterator , n3EditorDiagramModel);
				this.createGeneralizeLineModel(umConcreteAggregate,umAggregate , n3EditorDiagramModel);
				this.createAssociateLineModelModel(umConcreteIterator, umConcreteAggregate, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ");
				this.createDependencyLineModel(umConcreteAggregate, umConcreteIterator, n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==219){

				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umMediator= new FinalClassModel();
				umMediator.setName("Mediator");
			
				FinalClassModel umColleague= new FinalClassModel();
				umColleague.setName("Colleague");
				
				FinalClassModel umConcreteMediator= new FinalClassModel();
				umConcreteMediator.setName("ConcreteMediator");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Mediator")){
						if(!item[i].getText(1).equals("Mediator")){
							FinalClassModel model=modelMatch(umMediator, item[i].getText(1));							
							if(model!=null){
								umMediator=diagramInModel(model,umMediator,n3EditorDiagramModel);
							}else{
								umMediator.setName(item[i].getText(1));
								this.makeTreeModel(umMediator, n3EditorDiagramModel);

							}
						}else{
							umMediator.setName(item[i].getText(1));
							this.makeTreeModel(umMediator, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Colleague")){
						if(!item[i].getText(1).equals("Colleague")){
							FinalClassModel model=modelMatch(umColleague, item[i].getText(1));							
							if(model!=null){
								umColleague=diagramInModel(model,umColleague,n3EditorDiagramModel);
							}else{
								umColleague.setName(item[i].getText(1));
								this.makeTreeModel(umColleague, n3EditorDiagramModel);

							}
						}else{
							umColleague.setName(item[i].getText(1));
							this.makeTreeModel(umColleague, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteMediator")){
						if(!item[i].getText(1).equals("ConcreteMediator")){
							FinalClassModel model=modelMatch(umConcreteMediator, item[i].getText(1));							
							if(model!=null){
								umConcreteMediator=diagramInModel(model,umConcreteMediator,n3EditorDiagramModel);
							}else{
								umConcreteMediator.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteMediator, n3EditorDiagramModel);

							}
						}else{
							umConcreteMediator.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteMediator, n3EditorDiagramModel);

						}			
					}
				}
				
				umMediator.setLocation(new Point(44,59));
				umColleague.setLocation(new Point(266,59));
				umConcreteMediator.setLocation(new Point(44,216));
				
				n3EditorDiagramModel.addChild(umMediator);
				n3EditorDiagramModel.addChild(umColleague);
				n3EditorDiagramModel.addChild(umConcreteMediator);
				
				this.createAssociateLineModelModel(umColleague, umMediator, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=mediator");
				this.createGeneralizeLineModel(umConcreteMediator, umMediator, n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}else if(defineValue==220){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umOriginator = new FinalClassModel();
				umOriginator.setName("Originator");
				java.util.ArrayList umOriginatorOper = new ArrayList();
				OperationEditableTableItem opiOriginator = new OperationEditableTableItem();
				ParameterEditableTableItem parOriginator = new ParameterEditableTableItem();
				java.util.ArrayList umOriginatorPar = new ArrayList();
				opiOriginator.stype = "void";
				opiOriginator.name = "SetMemento";
				opiOriginator.scope = 0;
				parOriginator.name="m";
				parOriginator.stype="Memento";
				parOriginator.defalut="";
				umOriginatorPar.add(parOriginator);	
				opiOriginator.setParams(umOriginatorPar);
				umOriginatorOper.add(opiOriginator);
				opiOriginator = new OperationEditableTableItem();
				opiOriginator.stype = "void";
				opiOriginator.name = "CreateMemento";
				opiOriginator.scope = 0;
				umOriginatorOper.add(opiOriginator);			
				
				
				umOriginator.getClassModel().setOperations(umOriginatorOper);
				
				FinalClassModel umMemento = new FinalClassModel();
				umMemento.setName("Memento");
				java.util.ArrayList umMementoOper = new ArrayList();
				OperationEditableTableItem opiMemento = new OperationEditableTableItem();
				opiMemento.stype = "void";
				opiMemento.name = "GetState";
				opiMemento.scope = 0;
				umMementoOper.add(opiMemento);
				opiMemento = new OperationEditableTableItem();
				opiMemento.stype = "void";
				opiMemento.name = "SetState";
				opiMemento.scope = 0;
				umMementoOper.add(opiMemento);
				
				umMemento.getClassModel().setOperations(umMementoOper);
				
				FinalClassModel umCaretaker = new FinalClassModel();
				umMemento.setName("Caretaker");
				
				umOriginator.setSize(new Dimension(194,67));
				umMemento.setSize(new Dimension(112,67));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Originator")){
						if(!item[i].getText(1).equals("Originator")){
							FinalClassModel model=modelMatch(umOriginator, item[i].getText(1));							
							if(model!=null){
								umOriginator=diagramInModel(model,umOriginator,n3EditorDiagramModel);
							}else{
								umOriginator.setName(item[i].getText(1));
								this.makeTreeModel(umOriginator, n3EditorDiagramModel);

							}
						}else{
							umOriginator.setName(item[i].getText(1));
							this.makeTreeModel(umOriginator, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Memento")){
						if(!item[i].getText(1).equals("Memento")){
							FinalClassModel model=modelMatch(umMemento, item[i].getText(1));							
							if(model!=null){
								umMemento=diagramInModel(model,umMemento,n3EditorDiagramModel);
							}else{
								umMemento.setName(item[i].getText(1));
								this.makeTreeModel(umMemento, n3EditorDiagramModel);

							}
						}else{
							umMemento.setName(item[i].getText(1));
							this.makeTreeModel(umMemento, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Caretaker")){
						if(!item[i].getText(1).equals("Caretaker")){
							FinalClassModel model=modelMatch(umCaretaker, item[i].getText(1));							
							if(model!=null){
								umCaretaker=diagramInModel(model,umCaretaker,n3EditorDiagramModel);
							}else{
								umCaretaker.setName(item[i].getText(1));
								this.makeTreeModel(umCaretaker, n3EditorDiagramModel);

							}
						}else{
							umCaretaker.setName(item[i].getText(1));
							this.makeTreeModel(umCaretaker, n3EditorDiagramModel);

						}			
					}
				}
				
				umOriginator.setLocation(new Point(30,57));
				umMemento.setLocation(new Point(300,57));
				umCaretaker.setLocation(new Point(500,66));
				

				
				n3EditorDiagramModel.addChild(umOriginator);
				n3EditorDiagramModel.addChild(umMemento);
				n3EditorDiagramModel.addChild(umCaretaker);
				
				this.createAssociateLineModelModel(umCaretaker, umMemento, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_ROLE+"=menento");
				this.createDependencyLineModel(umOriginator, umMemento, n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==221){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umSubject = new FinalClassModel();
				umSubject.setName("Subject");
				java.util.ArrayList umSubjectOper = new ArrayList();
				java.util.ArrayList umSubjectPar = new ArrayList();
				OperationEditableTableItem opiSubject = new OperationEditableTableItem();
				ParameterEditableTableItem parSubject = new ParameterEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Attach";
				opiSubject.scope = 0;
				parSubject.name="o";
				parSubject.stype="Observer";
				parSubject.defalut="";
				umSubjectPar.add(parSubject);	
				opiSubject.setParams(umSubjectPar);
				umSubjectOper.add(opiSubject);
				
				umSubjectPar = new ArrayList();
				opiSubject = new OperationEditableTableItem();
				parSubject = new ParameterEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Detach";
				opiSubject.scope = 0;
				parSubject.name="o";
				parSubject.stype="Observer";
				parSubject.defalut="";
				umSubjectPar.add(parSubject);	
				opiSubject.setParams(umSubjectPar);
				umSubjectOper.add(opiSubject);
				
				opiSubject = new OperationEditableTableItem();
				parSubject = new ParameterEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Notify";
				opiSubject.scope = 0;
				umSubjectOper.add(opiSubject);		
				
				umSubject.getClassModel().setOperations(umSubjectOper);
				
				FinalClassModel umObserver = new FinalClassModel();
				umSubject.setName("Observer");
				java.util.ArrayList umObserverOper = new ArrayList();
				OperationEditableTableItem opiObserver = new OperationEditableTableItem();
				opiObserver.stype = "void";
				opiObserver.name = "Update";
				opiObserver.scope = 0;
				umObserverOper.add(opiObserver);				
				umObserver.getClassModel().setOperations(umObserverOper);
				
				FinalClassModel umConcreteSubject = new FinalClassModel();
				umSubject.setName("ConcreteSubject");
				
				FinalClassModel umConcreteObserver = new FinalClassModel();
				umSubject.setName("ConcreteObserver");
				
				umSubject.setSize(new Dimension(112,85));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Subject")){
						if(!item[i].getText(1).equals("Subject")){
							FinalClassModel model=modelMatch(umSubject, item[i].getText(1));							
							if(model!=null){
								umSubject=diagramInModel(model,umSubject,n3EditorDiagramModel);
							}else{
								umSubject.setName(item[i].getText(1));
								this.makeTreeModel(umSubject, n3EditorDiagramModel);

							}
						}else{
							umSubject.setName(item[i].getText(1));
							this.makeTreeModel(umSubject, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Observer")){
						if(!item[i].getText(1).equals("Observer")){
							FinalClassModel model=modelMatch(umObserver, item[i].getText(1));							
							if(model!=null){
								umObserver=diagramInModel(model,umObserver,n3EditorDiagramModel);
							}else{
								umObserver.setName(item[i].getText(1));
								this.makeTreeModel(umObserver, n3EditorDiagramModel);

							}
						}else{
							umObserver.setName(item[i].getText(1));
							this.makeTreeModel(umObserver, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteSubject")){
						if(!item[i].getText(1).equals("ConcreteSubject")){
							FinalClassModel model=modelMatch(umConcreteSubject, item[i].getText(1));							
							if(model!=null){
								umConcreteSubject=diagramInModel(model,umConcreteSubject,n3EditorDiagramModel);
							}else{
								umConcreteSubject.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteSubject, n3EditorDiagramModel);

							}
						}else{
							umConcreteSubject.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteSubject, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteObserver")){
						if(!item[i].getText(1).equals("ConcreteObserver")){
							FinalClassModel model=modelMatch(umConcreteObserver, item[i].getText(1));							
							if(model!=null){
								umConcreteObserver=diagramInModel(model,umConcreteObserver,n3EditorDiagramModel);
							}else{
								umConcreteObserver.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteObserver, n3EditorDiagramModel);

							}
						}else{
							umConcreteObserver.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteObserver, n3EditorDiagramModel);

						}			
					}
				}
				
				umSubject.setLocation(new Point(36,29));
				umObserver.setLocation(new Point(255,47));
				umConcreteSubject.setLocation(new Point(38,192));
				umConcreteObserver.setLocation(new Point(255,192));
				

				
				n3EditorDiagramModel.addChild(umSubject);
				n3EditorDiagramModel.addChild(umObserver);
				n3EditorDiagramModel.addChild(umConcreteSubject);
				n3EditorDiagramModel.addChild(umConcreteObserver);
				
				this.createAssociateLineModelModel(umSubject, umObserver, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=subject");
				this.createGeneralizeLineModel(umConcreteSubject, umSubject, n3EditorDiagramModel);
				this.createGeneralizeLineModel(umConcreteObserver, umObserver, n3EditorDiagramModel);
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());

			}else if(defineValue==222){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umClient = new FinalClassModel();
				umClient.setName("Client");
				
				FinalClassModel umPrototype = new FinalClassModel();
				umPrototype.setName("Prototype");
				java.util.ArrayList umPrototypeOper = new ArrayList();
				OperationEditableTableItem opiPrototype = new OperationEditableTableItem();
				opiPrototype.stype = "void";
				opiPrototype.name = "Clone";
				opiPrototype.scope = 0;
				umPrototypeOper.add(opiPrototype);	
				umPrototype.getClassModel().setOperations(umPrototypeOper);
				
				
				FinalClassModel umConcretePrototype = new FinalClassModel();
				umConcretePrototype.setName("ConcretePrototype");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Client")){
						if(!item[i].getText(1).equals("Client")){
							FinalClassModel model=modelMatch(umClient, item[i].getText(1));							
							if(model!=null){
								umClient=diagramInModel(model,umClient,n3EditorDiagramModel);
							}else{
								umClient.setName(item[i].getText(1));
								this.makeTreeModel(umClient, n3EditorDiagramModel);

							}
						}else{
							umClient.setName(item[i].getText(1));
							this.makeTreeModel(umClient, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Prototype")){
						if(!item[i].getText(1).equals("Prototype")){
							FinalClassModel model=modelMatch(umPrototype, item[i].getText(1));							
							if(model!=null){
								umPrototype=diagramInModel(model,umPrototype,n3EditorDiagramModel);
							}else{
								umPrototype.setName(item[i].getText(1));
								this.makeTreeModel(umPrototype, n3EditorDiagramModel);

							}
						}else{
							umPrototype.setName(item[i].getText(1));
							this.makeTreeModel(umPrototype, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcretePrototype")){
						if(!item[i].getText(1).equals("ConcretePrototype")){
							FinalClassModel model=modelMatch(umConcretePrototype, item[i].getText(1));							
							if(model!=null){
								umConcretePrototype=diagramInModel(model,umConcretePrototype,n3EditorDiagramModel);
							}else{
								umConcretePrototype.setName(item[i].getText(1));
								this.makeTreeModel(umConcretePrototype, n3EditorDiagramModel);

							}
						}else{
							umConcretePrototype.setName(item[i].getText(1));
							this.makeTreeModel(umConcretePrototype, n3EditorDiagramModel);

						}			
					}
				}
				umClient.setLocation(new Point(100,94));
				umPrototype.setLocation(new Point(300,94));
				umConcretePrototype.setLocation(new Point(300,204));
				
				n3EditorDiagramModel.addChild(umClient);
				n3EditorDiagramModel.addChild(umPrototype);
				n3EditorDiagramModel.addChild(umConcretePrototype);

				
				this.createAssociateLineModelModel(umClient, umPrototype, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=prototype");
				this.createGeneralizeLineModel(umConcretePrototype, umPrototype, n3EditorDiagramModel);
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}else if(defineValue==223){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umSubject = new FinalClassModel();
				umSubject.setName("Subject");
				java.util.ArrayList umSubjectOper = new ArrayList();
				java.util.ArrayList umSubjectPar = new ArrayList();
				OperationEditableTableItem opiSubject = new OperationEditableTableItem();
				ParameterEditableTableItem parSubject = new ParameterEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Attach";
				opiSubject.scope = 0;
				parSubject.name="o";
				parSubject.stype="Observer";
				parSubject.defalut="";
				umSubjectPar.add(parSubject);	
				opiSubject.setParams(umSubjectPar);
				umSubjectOper.add(opiSubject);
				umSubjectPar = new ArrayList();
				opiSubject = new OperationEditableTableItem();
				parSubject = new ParameterEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Detach";
				opiSubject.scope = 0;
				parSubject.name="o";
				parSubject.stype="Observer";
				parSubject.defalut="";
				umSubjectPar.add(parSubject);	
				opiSubject.setParams(umSubjectPar);
				umSubjectOper.add(opiSubject);
				opiSubject = new OperationEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Notify";
				opiSubject.scope = 0;
				umSubjectOper.add(opiSubject);
				opiSubject = new OperationEditableTableItem();
				opiSubject.stype = "void";
				opiSubject.name = "Request";
				opiSubject.scope = 0;
				umSubjectOper.add(opiSubject);
				
				umSubject.getClassModel().setOperations(umSubjectOper);
				
				FinalClassModel umRealSubject = new FinalClassModel();
				umRealSubject.setName("RealSubject");
				
				FinalClassModel umProxy = new FinalClassModel();
				umProxy.setName("Proxy");
				
				umSubject.setSize(new Dimension(153,102));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Subject")){
						if(!item[i].getText(1).equals("Subject")){
							FinalClassModel model=modelMatch(umSubject, item[i].getText(1));							
							if(model!=null){
								umSubject=diagramInModel(model,umSubject,n3EditorDiagramModel);
							}else{
								umSubject.setName(item[i].getText(1));
								this.makeTreeModel(umSubject, n3EditorDiagramModel);

							}
						}else{
							umSubject.setName(item[i].getText(1));
							this.makeTreeModel(umSubject, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("RealSubject")){
						if(!item[i].getText(1).equals("RealSubject")){
							FinalClassModel model=modelMatch(umRealSubject, item[i].getText(1));							
							if(model!=null){
								umRealSubject=diagramInModel(model,umRealSubject,n3EditorDiagramModel);
							}else{
								umRealSubject.setName(item[i].getText(1));
								this.makeTreeModel(umRealSubject, n3EditorDiagramModel);

							}
						}else{
							umRealSubject.setName(item[i].getText(1));
							this.makeTreeModel(umRealSubject, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Proxy")){
						if(!item[i].getText(1).equals("Proxy")){
							FinalClassModel model=modelMatch(umProxy, item[i].getText(1));							
							if(model!=null){
								umProxy=diagramInModel(model,umProxy,n3EditorDiagramModel);
							}else{
								umProxy.setName(item[i].getText(1));
								this.makeTreeModel(umProxy, n3EditorDiagramModel);

							}
						}else{
							umProxy.setName(item[i].getText(1));
							this.makeTreeModel(umProxy, n3EditorDiagramModel);

						}			
					}
				}
				
				
				umSubject.setLocation(new Point(118,3));
				umRealSubject.setLocation(new Point(28,197));
				umProxy.setLocation(new Point(252,198));
				
	
				
				n3EditorDiagramModel.addChild(umSubject);
				n3EditorDiagramModel.addChild(umRealSubject);
				n3EditorDiagramModel.addChild(umProxy);
				
				this.createGeneralizeLineModel(umRealSubject, umSubject, n3EditorDiagramModel, "Bendpoint=(0.5:0:-71:-110:97),Bendpoint=(0.5:108:-71:-2:97)");
				this.createGeneralizeLineModel(umProxy, umSubject, n3EditorDiagramModel, "Bendpoint=(0.5:-1:-72:113:97),Bendpoint=(0.5:-115:-72:-1:97)");
				this.createAssociateLineModelModel(umProxy, umRealSubject, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=RealSubject");

				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}else if(defineValue==224){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umSingleton = new FinalClassModel();
				umSingleton.setName("Singleton");
				java.util.ArrayList umSingletonOper = new ArrayList();
				OperationEditableTableItem opiSingletont = new OperationEditableTableItem();
				opiSingletont.stype = "Singleton";
				opiSingletont.name = "Instance";
				opiSingletont.scope = 0;
	
				umSingletonOper.add(opiSingletont);
				umSingleton.getClassModel().setOperations(umSingletonOper);

				umSingleton.setSize(new Dimension(136,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Singleton")){
						if(!item[i].getText(1).equals("Singleton")){
							FinalClassModel model=modelMatch(umSingleton, item[i].getText(1));							
							if(model!=null){
								umSingleton=diagramInModel(model,umSingleton,n3EditorDiagramModel);
							}else{
								umSingleton.setName(item[i].getText(1));
								this.makeTreeModel(umSingleton, n3EditorDiagramModel);

							}
						}else{
							umSingleton.setName(item[i].getText(1));
							this.makeTreeModel(umSingleton, n3EditorDiagramModel);

						}			
					}
				}
				
				umSingleton.setLocation(new Point(118,3));

				
				n3EditorDiagramModel.addChild(umSingleton);
				
				this.createAssociateLineModelModel(umSingleton, umSingleton, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_ROLE+"=instance");
				

				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==225){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umContext = new FinalClassModel();
				umContext.setName("Context");
				java.util.ArrayList umContextOper = new ArrayList();
				OperationEditableTableItem opiContext = new OperationEditableTableItem();
				opiContext.stype = "void";
				opiContext.name = "Request";
				opiContext.scope = 0;	
				umContextOper.add(opiContext);
				umContext.getClassModel().setOperations(umContextOper);
				
				FinalClassModel umState = new FinalClassModel();
				umContext.setName("State");
				java.util.ArrayList umStateOper = new ArrayList();
				OperationEditableTableItem opiState = new OperationEditableTableItem();
				opiState.stype = "void";
				opiState.name = "Handle";
				opiState.scope = 0;
	
				umStateOper.add(opiState);
				umState.getClassModel().setOperations(umStateOper);	
				
				FinalClassModel umConcreteState = new FinalClassModel();
				umConcreteState.setName("ConcreteState");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Context")){
						if(!item[i].getText(1).equals("Context")){
							FinalClassModel model=modelMatch(umContext, item[i].getText(1));							
							if(model!=null){
								umContext=diagramInModel(model,umContext,n3EditorDiagramModel);
							}else{
								umContext.setName(item[i].getText(1));
								this.makeTreeModel(umContext, n3EditorDiagramModel);

							}
						}else{
							umContext.setName(item[i].getText(1));
							this.makeTreeModel(umContext, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("State")){
						if(!item[i].getText(1).equals("State")){
							FinalClassModel model=modelMatch(umState, item[i].getText(1));							
							if(model!=null){
								umState=diagramInModel(model,umState,n3EditorDiagramModel);
							}else{
								umState.setName(item[i].getText(1));
								this.makeTreeModel(umState, n3EditorDiagramModel);

							}
						}else{
							umState.setName(item[i].getText(1));
							this.makeTreeModel(umState, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteState")){
						if(!item[i].getText(1).equals("ConcreteState")){
							FinalClassModel model=modelMatch(umConcreteState, item[i].getText(1));							
							if(model!=null){
								umConcreteState=diagramInModel(model,umConcreteState,n3EditorDiagramModel);
							}else{
								umConcreteState.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteState, n3EditorDiagramModel);

							}
						}else{
							umConcreteState.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteState, n3EditorDiagramModel);

						}			
					}
				}
				
				umContext.setLocation(new Point(68,83));
				umState.setLocation(new Point(364,83));
				umConcreteState.setLocation(new Point(68,302));
				
				n3EditorDiagramModel.addChild(umContext);
				n3EditorDiagramModel.addChild(umState);
				n3EditorDiagramModel.addChild(umConcreteState);
				
				this.createAssociateLineModelModel(umContext, umState, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_ROLE+"=state");
				this.createGeneralizeLineModel(umConcreteState,umState , n3EditorDiagramModel);
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}else if(defineValue==226){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umContext = new FinalClassModel();
				umContext.setName("Context");
				java.util.ArrayList umContextOper = new ArrayList();
				OperationEditableTableItem opiContext = new OperationEditableTableItem();
				opiContext.stype = "void";
				opiContext.name = "Request";
				opiContext.scope = 0;	
				umContextOper.add(opiContext);
				opiContext = new OperationEditableTableItem();
				opiContext.stype = "void";
				opiContext.name = "ContextInterface";
				opiContext.scope = 0;	
				umContextOper.add(opiContext);
				umContext.getClassModel().setOperations(umContextOper);
				
				FinalClassModel umStrategy = new FinalClassModel();
				umContext.setName("Strategy");
				java.util.ArrayList umStrategyOper = new ArrayList();
				OperationEditableTableItem opiStrategy = new OperationEditableTableItem();
				opiStrategy.stype = "void";
				opiStrategy.name = "AlgorithmInterface";
				opiStrategy.scope = 0;
	
				umStrategyOper.add(opiStrategy);
				umStrategy.getClassModel().setOperations(umStrategyOper);	
				
				FinalClassModel umConcreteStrategy = new FinalClassModel();
				umConcreteStrategy.setName("ConcreteStrategy");
				
				umContext.setSize(new Dimension(147,68));
				umStrategy.setSize(new Dimension(152,50));
				
				
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Context")){
						if(!item[i].getText(1).equals("Context")){
							FinalClassModel model=modelMatch(umContext, item[i].getText(1));							
							if(model!=null){
								umContext=diagramInModel(model,umContext,n3EditorDiagramModel);
							}else{
								umContext.setName(item[i].getText(1));
								this.makeTreeModel(umContext, n3EditorDiagramModel);

							}
						}else{
							umContext.setName(item[i].getText(1));
							this.makeTreeModel(umContext, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Strategy")){
						if(!item[i].getText(1).equals("Strategy")){
							FinalClassModel model=modelMatch(umStrategy, item[i].getText(1));							
							if(model!=null){
								umStrategy=diagramInModel(model,umStrategy,n3EditorDiagramModel);
							}else{
								umStrategy.setName(item[i].getText(1));
								this.makeTreeModel(umStrategy, n3EditorDiagramModel);

							}
						}else{
							umStrategy.setName(item[i].getText(1));
							this.makeTreeModel(umStrategy, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteStrategy")){
						if(!item[i].getText(1).equals("ConcreteStrategy")){
							FinalClassModel model=modelMatch(umConcreteStrategy, item[i].getText(1));							
							if(model!=null){
								umConcreteStrategy=diagramInModel(model,umConcreteStrategy,n3EditorDiagramModel);
							}else{
								umConcreteStrategy.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteStrategy, n3EditorDiagramModel);

							}
						}else{
							umConcreteStrategy.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteStrategy, n3EditorDiagramModel);

						}			
					}
				}
				
				umContext.setLocation(new Point(68,75));
				umStrategy.setLocation(new Point(364,83));
				umConcreteStrategy.setLocation(new Point(68,302));
				

				n3EditorDiagramModel.addChild(umContext);
				n3EditorDiagramModel.addChild(umStrategy);
				n3EditorDiagramModel.addChild(umConcreteStrategy);
				
				this.createAssociateLineModelModel(umContext, umStrategy, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_SOURCE_AGGREGATION+"=Aggregate,"+LineModel.ID_TARGET_ROLE+"=strategy");
				this.createGeneralizeLineModel(umConcreteStrategy,umStrategy , n3EditorDiagramModel);
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
				
			}else if(defineValue==227){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umAbstractClass = new FinalClassModel();
				umAbstractClass.setName("AbstractClass");

				FinalClassModel umConcreteClass = new FinalClassModel();
				umConcreteClass.setName("ConcreteClass");
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("AbstractClass")){
						if(!item[i].getText(1).equals("AbstractClass")){
							FinalClassModel model=modelMatch(umAbstractClass, item[i].getText(1));							
							if(model!=null){
								umAbstractClass=diagramInModel(model,umAbstractClass,n3EditorDiagramModel);
							}else{
								umAbstractClass.setName(item[i].getText(1));
								this.makeTreeModel(umAbstractClass, n3EditorDiagramModel);

							}
						}else{
							umAbstractClass.setName(item[i].getText(1));
							this.makeTreeModel(umAbstractClass, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteClass")){
						if(!item[i].getText(1).equals("ConcreteClass")){
							FinalClassModel model=modelMatch(umConcreteClass, item[i].getText(1));							
							if(model!=null){
								umConcreteClass=diagramInModel(model,umConcreteClass,n3EditorDiagramModel);
							}else{
								umConcreteClass.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteClass, n3EditorDiagramModel);

							}
						}else{
							umConcreteClass.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteClass, n3EditorDiagramModel);

						}			
					}
				}
				
				umAbstractClass.setLocation(new Point(280,222));
				umConcreteClass.setLocation(new Point(280,335));
				
				
				n3EditorDiagramModel.addChild(umAbstractClass);
				n3EditorDiagramModel.addChild(umConcreteClass);
				
				
				this.createGeneralizeLineModel(umConcreteClass, umAbstractClass, n3EditorDiagramModel);
				

				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==228){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umVisitor = new FinalClassModel();
				umVisitor.setName("Visitor");
				java.util.ArrayList umVisitorOper = new ArrayList();
				OperationEditableTableItem opiVisitor = new OperationEditableTableItem();
				opiVisitor.stype = "void";
				opiVisitor.name = "VisitConcreateElement";
				opiVisitor.scope = 0;	
				java.util.ArrayList umVisitorPar = new ArrayList();
				ParameterEditableTableItem parVisitor = new ParameterEditableTableItem();
				parVisitor.name="elem";
				parVisitor.stype="ConcreateElement";
				parVisitor.defalut="";
				umVisitorPar.add(parVisitor);
				opiVisitor.setParams(umVisitorPar);
				umVisitorOper.add(opiVisitor);
				umVisitor.getClassModel().setOperations(umVisitorOper);
				
				FinalClassModel umElement = new FinalClassModel();
				umElement.setName("Element");
				java.util.ArrayList umElementOper = new ArrayList();
				OperationEditableTableItem opiElement = new OperationEditableTableItem();
				opiElement.stype = "void";
				opiElement.name = "Accept";
				opiElement.scope = 0;	
				java.util.ArrayList umElementPar = new ArrayList();
				ParameterEditableTableItem parElement = new ParameterEditableTableItem();
				parElement.name="v";
				parElement.stype="Visitor";
				parElement.defalut="";
				umElementPar.add(parElement);
				opiElement.setParams(umElementPar);
				umElementOper.add(opiElement);
				
				umElement.getClassModel().setOperations(umElementOper);
				
				FinalClassModel umObjectStruture = new FinalClassModel();
				umObjectStruture.setName("ObjectStruture");
				
				FinalClassModel umConcreteVisitor = new FinalClassModel();
				umConcreteVisitor.setName("ConcreteVisitor");
				
				FinalClassModel umConcreateElement = new FinalClassModel();
				umConcreateElement.setName("ConcreateElement");
				
				umVisitor.setSize(new Dimension(334,50));
				umElement.setSize(new Dimension(137,50));
				umConcreateElement.setSize(new Dimension(138,50));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("Visitor")){
						if(!item[i].getText(1).equals("Visitor")){
							FinalClassModel model=modelMatch(umVisitor, item[i].getText(1));							
							if(model!=null){
								umVisitor=diagramInModel(model,umVisitor,n3EditorDiagramModel);
							}else{
								umVisitor.setName(item[i].getText(1));
								this.makeTreeModel(umVisitor, n3EditorDiagramModel);

							}
						}else{
							umVisitor.setName(item[i].getText(1));
							this.makeTreeModel(umVisitor, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Element")){
						if(!item[i].getText(1).equals("Element")){
							FinalClassModel model=modelMatch(umElement, item[i].getText(1));							
							if(model!=null){
								umElement=diagramInModel(model,umElement,n3EditorDiagramModel);
							}else{
								umElement.setName(item[i].getText(1));
								this.makeTreeModel(umElement, n3EditorDiagramModel);

							}
						}else{
							umElement.setName(item[i].getText(1));
							this.makeTreeModel(umElement, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ObjectStruture")){
						if(!item[i].getText(1).equals("ObjectStruture")){
							FinalClassModel model=modelMatch(umObjectStruture, item[i].getText(1));							
							if(model!=null){
								umObjectStruture=diagramInModel(model,umObjectStruture,n3EditorDiagramModel);
							}else{
								umObjectStruture.setName(item[i].getText(1));
								this.makeTreeModel(umObjectStruture, n3EditorDiagramModel);

							}
						}else{
							umObjectStruture.setName(item[i].getText(1));
							this.makeTreeModel(umObjectStruture, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreteVisitor")){
						if(!item[i].getText(1).equals("ConcreteVisitor")){
							FinalClassModel model=modelMatch(umConcreteVisitor, item[i].getText(1));							
							if(model!=null){
								umConcreteVisitor=diagramInModel(model,umConcreteVisitor,n3EditorDiagramModel);
							}else{
								umConcreteVisitor.setName(item[i].getText(1));
								this.makeTreeModel(umConcreteVisitor, n3EditorDiagramModel);

							}
						}else{
							umConcreteVisitor.setName(item[i].getText(1));
							this.makeTreeModel(umConcreteVisitor, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("ConcreateElement")){
						if(!item[i].getText(1).equals("ConcreateElement")){
							FinalClassModel model=modelMatch(umConcreateElement, item[i].getText(1));							
							if(model!=null){
								umConcreateElement=diagramInModel(model,umConcreateElement,n3EditorDiagramModel);
							}else{
								umConcreateElement.setName(item[i].getText(1));
								this.makeTreeModel(umConcreateElement, n3EditorDiagramModel);

							}
						}else{
							umConcreateElement.setName(item[i].getText(1));
							this.makeTreeModel(umConcreateElement, n3EditorDiagramModel);

						}			
					}
				}

				
				umVisitor.setLocation(new Point(30,51));
				umElement.setLocation(new Point(406,51));
				umObjectStruture.setLocation(new Point(590,51));
				umConcreteVisitor.setLocation(new Point(142,255));
				umConcreateElement.setLocation(new Point(405,255));
				

				
				n3EditorDiagramModel.addChild(umVisitor);
				n3EditorDiagramModel.addChild(umElement);
				n3EditorDiagramModel.addChild(umObjectStruture);
				n3EditorDiagramModel.addChild(umConcreteVisitor);
				n3EditorDiagramModel.addChild(umConcreateElement);
				
				this.createGeneralizeLineModel(umConcreteVisitor, umVisitor, n3EditorDiagramModel);
				this.createGeneralizeLineModel(umConcreateElement, umElement, n3EditorDiagramModel);
				
				this.createAssociateLineModelModel(umObjectStruture, umElement, n3EditorDiagramModel,LineModel.ID_TARGET_NAVIGABILITY+"= ,"+LineModel.ID_TARGET_MUL+"=*");

				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==230){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umEntityBean = new FinalClassModel();
				umEntityBean.setName("EntityBean");
				umEntityBean.setStereotype("EJBImplementation");
				java.util.ArrayList umEntityBeanatt = new ArrayList();
				AttributeEditableTableItem attEntityBean = new AttributeEditableTableItem();
				attEntityBean.stype = "javax.ejb.EntityContext";
				attEntityBean.name = "EntityContext";
				attEntityBean.scope = 0;
				umEntityBeanatt.add(attEntityBean);
				attEntityBean = new AttributeEditableTableItem();
				attEntityBean.stype = "int";
				attEntityBean.name = "foofield";
				attEntityBean.scope = 0;
				umEntityBeanatt.add(attEntityBean);
				umEntityBean.getClassModel().setAttribute(umEntityBeanatt);
				
				java.util.ArrayList umEntityBeanOper = new ArrayList();
				OperationEditableTableItem opiEntityBean = new OperationEditableTableItem();
				java.util.ArrayList umEntityBeanPar = new ArrayList();
				ParameterEditableTableItem parEntityBean = new ParameterEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "setEntityContext";
				opiEntityBean.scope = 0;				
				parEntityBean.name="context";
				parEntityBean.stype="javax.ejb.EntityContext";
				parEntityBean.defalut="";
				umEntityBeanPar.add(parEntityBean);
				opiEntityBean.setParams(umEntityBeanPar);				
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "unsetEntityContext";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbActivate";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbPassivate";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbRemove";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbStore";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbLoad";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "EntityPK";
				opiEntityBean.name = "ejbCreate";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "ejbPostCreate";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				opiEntityBean.stype = "int";
				opiEntityBean.name = "getFooField";
				opiEntityBean.scope = 0;
				umEntityBeanOper.add(opiEntityBean);
				opiEntityBean = new OperationEditableTableItem();
				umEntityBeanPar = new ArrayList();
				parEntityBean = new ParameterEditableTableItem();
				opiEntityBean.stype = "void";
				opiEntityBean.name = "setFooField";
				opiEntityBean.scope = 0;
				parEntityBean.name="param";
				parEntityBean.stype="int";
				parEntityBean.defalut="";
				umEntityBeanPar.add(parEntityBean);
				opiEntityBean.setParams(umEntityBeanPar);
				umEntityBeanOper.add(opiEntityBean);
				umEntityBean.getClassModel().setOperations(umEntityBeanOper);
				
				FinalClassModel umEntityPK = new FinalClassModel();
				umEntityPK.setName("EntityPK");
				
				FinalClassModel umEntityHome = new FinalClassModel();
				umEntityHome.setName("EntityHome");
				java.util.ArrayList umEntityHomeOper = new ArrayList();
				OperationEditableTableItem opiEntityHome = new OperationEditableTableItem();
				java.util.ArrayList umEntityHomePar = new ArrayList();
				ParameterEditableTableItem parEntityHome = new ParameterEditableTableItem();
				opiEntityHome = new OperationEditableTableItem();
				opiEntityHome.stype = " Entity";
				opiEntityHome.name = "create";
				opiEntityHome.scope = 0;
				umEntityHomeOper.add(opiEntityHome);
				opiEntityHome = new OperationEditableTableItem();
				opiEntityHome.stype = "Entity";
				opiEntityHome.name = "findByPrimaryKey";
				opiEntityHome.scope = 0;
				parEntityHome.name="pk";
				parEntityHome.stype="EntityPK";
				parEntityHome.defalut="";
				umEntityHomePar.add(parEntityHome);
				opiEntityHome.setParams(umEntityHomePar);
				umEntityHomeOper.add(opiEntityHome);
				
				umEntityHome.getClassModel().setOperations(umEntityHomeOper);
				
				FinalClassModel umEntity = new FinalClassModel();
				umEntity.setName("Entity");
				umEntity.setStereotype("EJBEntityHomeInterface");
				java.util.ArrayList umEntityOper = new ArrayList();
				OperationEditableTableItem opiEntity = new OperationEditableTableItem();
				opiEntity.stype = "int";
				opiEntity.name = "getFooField";
				opiEntity.scope = 0;
				umEntityOper.add(opiEntity);
				java.util.ArrayList umEntityPar = new ArrayList();
				ParameterEditableTableItem parEntity = new ParameterEditableTableItem();
				opiEntity = new OperationEditableTableItem();
				opiEntity.stype = "void";
				opiEntity.name = "setFooField";
				opiEntity.scope = 0;
				parEntity.name="param";
				parEntity.stype="int";
				parEntity.defalut="";
				umEntityPar.add(parEntity);
				opiEntity.setParams(umEntityPar);
				umEntityOper.add(opiEntity);
				umEntity.getClassModel().setOperations(umEntityOper);
				
				umEntityBean.setSize(new Dimension(354,250));
				umEntityPK.setSize(new Dimension(112,51));
				umEntityHome.setSize(new Dimension(237,73));
				umEntity.setSize(new Dimension(171,77));
				
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("EntityBean")){
						if(!item[i].getText(1).equals("EntityBean")){
							FinalClassModel model=modelMatch(umEntityBean, item[i].getText(1));							
							if(model!=null){
								umEntityBean=diagramInModel(model,umEntityBean,n3EditorDiagramModel);
							}else{
								umEntityBean.setName(item[i].getText(1));
								this.makeTreeModel(umEntityBean, n3EditorDiagramModel);

							}
						}else{
							umEntityBean.setName(item[i].getText(1));
							this.makeTreeModel(umEntityBean, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("EJB Primary Key Class")){
						if(!item[i].getText(1).equals("EntityPK")){
							FinalClassModel model=modelMatch(umEntityPK, item[i].getText(1));							
							if(model!=null){
								umEntityPK=diagramInModel(model,umEntityPK,n3EditorDiagramModel);
							}else{
								umEntityPK.setName(item[i].getText(1));
								this.makeTreeModel(umEntityPK, n3EditorDiagramModel);

							}
						}else{
							umEntityPK.setName(item[i].getText(1));
							this.makeTreeModel(umEntityPK, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("EntityHome")){
						if(!item[i].getText(1).equals("EntityHome")){
							FinalClassModel model=modelMatch(umEntityHome, item[i].getText(1));							
							if(model!=null){
								umEntityHome=diagramInModel(model,umEntityHome,n3EditorDiagramModel);
							}else{
								umEntityHome.setName(item[i].getText(1));
								this.makeTreeModel(umEntityHome, n3EditorDiagramModel);

							}
						}else{
							umEntityHome.setName(item[i].getText(1));
							this.makeTreeModel(umEntityHome, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("Remote Interface")){
						if(!item[i].getText(1).equals("Entity")){
							FinalClassModel model=modelMatch(umEntity, item[i].getText(1));							
							if(model!=null){
								umEntity=diagramInModel(model,umEntity,n3EditorDiagramModel);
							}else{
								umEntity.setName(item[i].getText(1));
								this.makeTreeModel(umEntity, n3EditorDiagramModel);

							}
						}else{
							umEntity.setName(item[i].getText(1));
							this.makeTreeModel(umEntity, n3EditorDiagramModel);

						}			
					}
				}
				umEntityBean.setLocation(new Point(14,143));
				umEntityPK.setLocation(new Point(496,29));
				umEntityHome.setLocation(new Point(433,142));
				umEntity.setLocation(new Point(467,318));

				n3EditorDiagramModel.addChild(umEntityBean);
				n3EditorDiagramModel.addChild(umEntityPK);
				n3EditorDiagramModel.addChild(umEntityHome);
				n3EditorDiagramModel.addChild(umEntity);
				
				this.createDependencyLineModel(umEntityBean, umEntity, n3EditorDiagramModel,LineModel.ID_NAME+"=<<EJBRealizeRemote>>,"+LineModel.ID_TARGET_NAVIGABILITY+"= ,");//PKY 08070901 S 패턴 EjB틀린 내용 수정
				this.createDependencyLineModel(umEntityBean, umEntityHome, n3EditorDiagramModel,LineModel.ID_NAME+"=<<EJBRealizeHome>>,"+LineModel.ID_TARGET_NAVIGABILITY+"= ,");//PKY 08070901 S 패턴 EjB틀린 내용 수정
				this.createDependencyLineModel(umEntityHome, umEntityPK, n3EditorDiagramModel,LineModel.ID_NAME+"=<<EJBPrimaryKey>>,"+LineModel.ID_TARGET_NAVIGABILITY+"= ,");//PKY 08070901 S 패턴 EjB틀린 내용 수정
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==231){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umMessageDrivenBean = new FinalClassModel();
				umMessageDrivenBean.setName("MessageDrivenBean");
				umMessageDrivenBean.setStereotype("EJBImplementation");
				java.util.ArrayList MessageDrivenBeanatt = new ArrayList();
				AttributeEditableTableItem attMessageDrivenBean = new AttributeEditableTableItem();
				attMessageDrivenBean.stype = "javax.ejb.MessageDrivenContext";
				attMessageDrivenBean.name = "ctx";
				attMessageDrivenBean.scope = 0;
				MessageDrivenBeanatt.add(attMessageDrivenBean);
				umMessageDrivenBean.getClassModel().setAttribute(MessageDrivenBeanatt);
				
				java.util.ArrayList umMessageDrivenBeanOper = new ArrayList();
				OperationEditableTableItem opiMessageDrivenBean = new OperationEditableTableItem();
				java.util.ArrayList umMessageDrivenBeanPar = new ArrayList();
				ParameterEditableTableItem parMessageDrivenBean = new ParameterEditableTableItem();
				opiMessageDrivenBean.stype = "void";
				opiMessageDrivenBean.name = "MessageDrivenBean";
				opiMessageDrivenBean.scope = 0;		
				umMessageDrivenBeanOper.add(opiMessageDrivenBean);
				opiMessageDrivenBean = new OperationEditableTableItem();
				parMessageDrivenBean = new ParameterEditableTableItem();
				umMessageDrivenBeanPar = new ArrayList();				
				opiMessageDrivenBean.stype = "void";
				opiMessageDrivenBean.name = "setMessageDrivenContext";
				opiMessageDrivenBean.scope = 0;				
				parMessageDrivenBean.name="context";
				parMessageDrivenBean.stype="javax.ejb.MessageDrivenContext";
				parMessageDrivenBean.defalut="";
				umMessageDrivenBeanPar.add(parMessageDrivenBean);
				opiMessageDrivenBean.setParams(umMessageDrivenBeanPar);		
				umMessageDrivenBeanOper.add(opiMessageDrivenBean);
				opiMessageDrivenBean = new OperationEditableTableItem();
				opiMessageDrivenBean.stype = "void";
				opiMessageDrivenBean.name = "ejbCreate";
				opiMessageDrivenBean.scope = 0;		
				umMessageDrivenBeanOper.add(opiMessageDrivenBean);
				opiMessageDrivenBean = new OperationEditableTableItem();
				opiMessageDrivenBean.stype = "void";
				opiMessageDrivenBean.name = "ejbRemove";
				opiMessageDrivenBean.scope = 0;		
				umMessageDrivenBeanOper.add(opiMessageDrivenBean);

				opiMessageDrivenBean = new OperationEditableTableItem();
				parMessageDrivenBean = new ParameterEditableTableItem();
				umMessageDrivenBeanPar = new ArrayList();				
				opiMessageDrivenBean.stype = "void";
				opiMessageDrivenBean.name = "onMessage";
				opiMessageDrivenBean.scope = 0;				
				parMessageDrivenBean.name="msg";
				parMessageDrivenBean.stype="javax.jms.Message";
				parMessageDrivenBean.defalut="";
				umMessageDrivenBeanPar.add(parMessageDrivenBean);
				opiMessageDrivenBean.setParams(umMessageDrivenBeanPar);		
				umMessageDrivenBeanOper.add(opiMessageDrivenBean);
				
				umMessageDrivenBean.getClassModel().setOperations(umMessageDrivenBeanOper);
				
				umMessageDrivenBean.setSize(new Dimension(451,141));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("ClassName")){
						if(!item[i].getText(1).equals("MessageDrivenBean")){
							FinalClassModel model=modelMatch(umMessageDrivenBean, item[i].getText(1));							
							if(model!=null){
								umMessageDrivenBean=diagramInModel(model,umMessageDrivenBean,n3EditorDiagramModel);
							}else{
								umMessageDrivenBean.setName(item[i].getText(1));
								this.makeTreeModel(umMessageDrivenBean, n3EditorDiagramModel);

							}
						}else{
							umMessageDrivenBean.setName(item[i].getText(1));
							this.makeTreeModel(umMessageDrivenBean, n3EditorDiagramModel);

						}			
					}
				}
				
				n3EditorDiagramModel.addChild(umMessageDrivenBean);
				
				umMessageDrivenBean.setLocation(new Point(14,143));
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}else if(defineValue==232){
				
				N3EditorDiagramModel n3EditorDiagramModel = ProjectManager.getInstance().getUMLEditor().getDiagram();
				
				FinalClassModel umSessionBean = new FinalClassModel();
				umSessionBean.setName("SessionBean");
				umSessionBean.setStereotype("EJBImplementation");
				java.util.ArrayList SessionBeanatt = new ArrayList();
				AttributeEditableTableItem attSessionBean = new AttributeEditableTableItem();
				attSessionBean.stype = "javax.ejb.SessionContext";
				attSessionBean.name = "ctx";
				attSessionBean.scope = 0;
				SessionBeanatt.add(attSessionBean);
				umSessionBean.getClassModel().setAttribute(SessionBeanatt);
				java.util.ArrayList umSessionBeanOper = new ArrayList();
				OperationEditableTableItem opiSessionBean = new OperationEditableTableItem();
				java.util.ArrayList umSessionBeanPar = new ArrayList();
				ParameterEditableTableItem parSessionBean = new ParameterEditableTableItem();				
				opiSessionBean.stype = "void";
				opiSessionBean.name = "setSessionContext";
				opiSessionBean.scope = 0;				
				parSessionBean.name="context";
				parSessionBean.stype="javax.ejb.SessionContext";
				parSessionBean.defalut="";
				umSessionBeanPar.add(parSessionBean);
				opiSessionBean.setParams(umSessionBeanPar);
				umSessionBeanOper.add(opiSessionBean);
				opiSessionBean = new OperationEditableTableItem();
				opiSessionBean.stype = "void";
				opiSessionBean.name = "ejbActivate";
				opiSessionBean.scope = 0;		
				umSessionBeanOper.add(opiSessionBean);
				opiSessionBean = new OperationEditableTableItem();
				opiSessionBean.stype = "void";
				opiSessionBean.name = "ejbPassivate";
				opiSessionBean.scope = 0;		
				umSessionBeanOper.add(opiSessionBean);
				opiSessionBean = new OperationEditableTableItem();
				opiSessionBean.stype = "void";
				opiSessionBean.name = "ejbCreate";
				opiSessionBean.scope = 0;		
				umSessionBeanOper.add(opiSessionBean);
				opiSessionBean = new OperationEditableTableItem();
				opiSessionBean.stype = "void";
				opiSessionBean.name = "ejbRemove";
				opiSessionBean.scope = 0;		
				umSessionBeanOper.add(opiSessionBean);
				umSessionBean.getClassModel().setOperations(umSessionBeanOper);
				
				FinalClassModel umSession = new FinalClassModel();
				umSession.setName("Session");
				umSession.setStereotype("EJBRemoteInterface");

				FinalClassModel umSessionHome = new FinalClassModel();
				umSessionHome.setName("SessionHome");
				umSessionHome.setStereotype("EJBSessionHomeInterface");
				java.util.ArrayList umSessionHomeOper = new ArrayList();
				OperationEditableTableItem opiSessionHome  = new OperationEditableTableItem();
				opiSessionHome.stype = "Session";
				opiSessionHome.name = "create";
				opiSessionHome.scope = 0;	
				umSessionHomeOper.add(opiSessionHome);
				umSessionHome.getClassModel().setOperations(umSessionHomeOper);
				
				umSessionBean.setSize(new Dimension(350,140));
				umSession.setSize(new Dimension(161,65));
				umSessionHome.setSize(new Dimension(189,65));
				
				for(int i=0; i<item.length;i++){
					if(item[i].getText().equals("SessionBean")){
						if(!item[i].getText(1).equals("SessionBean")){
							FinalClassModel model=modelMatch(umSessionBean, item[i].getText(1));							
							if(model!=null){
								umSessionBean=diagramInModel(model,umSessionBean,n3EditorDiagramModel);
														
							}else{
								umSessionBean.setName(item[i].getText(1));
								this.makeTreeModel(umSessionBean, n3EditorDiagramModel);

							}
						}else{
							umSessionBean.setName(item[i].getText(1));
							this.makeTreeModel(umSessionBean, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("HomeInterface")){
						if(!item[i].getText(1).equals("SessionHome")){
							FinalClassModel model=modelMatch(umSessionHome, item[i].getText(1));							
							if(model!=null){
								umSessionHome=diagramInModel(model,umSessionHome,n3EditorDiagramModel);
							}else{
								umSessionHome.setName(item[i].getText(1));
								this.makeTreeModel(umSessionHome, n3EditorDiagramModel);

							}
						}else{
							umSessionHome.setName(item[i].getText(1));
							this.makeTreeModel(umSessionHome, n3EditorDiagramModel);

						}			
					}else if(item[i].getText().equals("RemoteInterface")){
						if(!item[i].getText(1).equals("Session")){
							FinalClassModel model=modelMatch(umSession, item[i].getText(1));							
							if(model!=null){
								umSession=diagramInModel(model,umSession,n3EditorDiagramModel);
							}else{
								umSession.setName(item[i].getText(1));
								this.makeTreeModel(umSession, n3EditorDiagramModel);

							}
						}else{
							umSession.setName(item[i].getText(1));
							this.makeTreeModel(umSession, n3EditorDiagramModel);

						}			
					}
				}
				
				n3EditorDiagramModel.addChild(umSessionBean);
				n3EditorDiagramModel.addChild(umSession);
				n3EditorDiagramModel.addChild(umSessionHome);
				
				umSessionBean.setLocation(new Point(21,62));
				umSession.setLocation(new Point(464,45));
				umSessionHome.setLocation(new Point(451,178));
				
				this.createDependencyLineModel(umSessionBean, umSession, n3EditorDiagramModel,LineModel.ID_NAME+"=<<EJBRealizeRemote>>,"+LineModel.ID_TARGET_NAVIGABILITY+"= ,"); //PKY 08070901 S 패턴 EjB틀린 내용 수정
				this.createDependencyLineModel(umSessionBean, umSessionHome, n3EditorDiagramModel,LineModel.ID_NAME+"=<<EJBRealizeHome>>,"+LineModel.ID_TARGET_NAVIGABILITY+"= ,");//PKY 08070901 S 패턴 EjB틀린 내용 수정
				
				
				
				ProjectManager.getInstance().getModelBrowser().refresh(n3EditorDiagramModel.getUMLTreeModel().getParent());
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}

		private FinalClassModel modelMatch(FinalClassModel model,String value){
			ArrayList array=ProjectManager.getInstance().getTypeModel();
			if(value.lastIndexOf(".")>-1){
			String url=value.substring(0,value.lastIndexOf("."));
			String name=value.substring(value.lastIndexOf(".")+1,value.length());
			for( int j = 0 ; j < array.size(); j++){
				if(array.get(j) instanceof StrcuturedPackageTreeModel){
					if(name.trim().equals(((StrcuturedPackageTreeModel)array.get(j)).getName().trim())){									
						model=(FinalClassModel)((StrcuturedPackageTreeModel)array.get(j)).getRefModel();
						if(model.getPackage().equals(url))
						return model;
					}
				}
			}
			}
			return null;
		}
		private FinalClassModel diagramInModel(FinalClassModel model,FinalClassModel value,N3EditorDiagramModel n3EditorDiagramModel){

			ArrayList arrayList=model.getClassModel().getAttributes();
			ArrayList vlarrayList=value.getClassModel().getAttributes();
			ArrayList operList=model.getClassModel().getOperations();
			ArrayList vloperList=value.getClassModel().getOperations();
				if(!value.getStereotype().trim().equals("")){
					model.setStereotype(value.getStereotype());
				}
			if(arrayList!=null){
				for(int i=0; i<arrayList.size();i++){
					vlarrayList.add(arrayList.get(i));
				}
				model.getClassModel().setAttributes(vlarrayList);
			}
			if(operList!=null){
				
				for(int i=0; i<operList.size();i++){
					vloperList.add(operList.get(i));
				}
				model.getClassModel().setOperations(vloperList);

			}
			int width=0;
			if(model.getSize().width>value.getSize().width){
				width=model.getSize().width;
			}else{
				width=value.getSize().width;
			}
			if(operList!=null||operList!=null){
				model.setSize(new Dimension(width,model.getSize().height));
				ProjectManager.getInstance().autoSize(model.getClassModel());

			}
			for(int j=0; j<n3EditorDiagramModel.getChildren().size();j++){
				if(n3EditorDiagramModel.getChildren().get(j) instanceof  FinalClassModel){
					if(((FinalClassModel)n3EditorDiagramModel.getChildren().get(j)).getClassModel() == model.getClassModel()){

						Vector s=((FinalClassModel)n3EditorDiagramModel.getChildren().get(j)).getTargetConnections();
						for(int k=0; k<s.size();k++){
							if( s.get(k) instanceof LineModel){
								LineModel lineModel=(LineModel)s.get(k);
								for (int b = 0; b < s.size(); b++) {
									LineModel wire = (LineModel)s.get(b);
									wire.detachSource();
									wire.detachTarget();
								}
								for (int b = 0; b < s.size(); b++) {
									LineModel wire = (LineModel)s.get(b);
									wire.detachSource();
									wire.detachTarget();
								}
							}						
						}
						s=((FinalClassModel)n3EditorDiagramModel.getChildren().get(j)).getSourceConnections();
						for(int k=0; k<s.size();k++){
							if( s.get(k) instanceof LineModel){
								LineModel lineModel=(LineModel)s.get(k);
								for (int b = 0; b < s.size(); b++) {
									LineModel wire = (LineModel)s.get(b);
									wire.detachSource();
									wire.detachTarget();
								}
								for (int b = 0; b < s.size(); b++) {
									LineModel wire = (LineModel)s.get(b);
									wire.detachSource();
									wire.detachTarget();
								}
							}						
						}


//						n3EditorDiagramModel.getChildren().remove(n3EditorDiagramModel.getChildren().get(j));
						n3EditorDiagramModel.removeModel((UMLModel)n3EditorDiagramModel.getChildren().get(j));



					}
				}
			}
//			for(int j = 0; j<model.getUMLTreeModel().getIN3UMLModelDeleteListeners().size(); j++){
//			IN3UMLModelDeleteListener l = (IN3UMLModelDeleteListener)model.getUMLTreeModel().getIN3UMLModelDeleteListeners().get(j);
//			if(n3EditorDiagramModel==model.getUMLTreeModel().getIN3UMLModelDeleteListeners().get(j)){
//			for( int p=0;p<n3EditorDiagramModel.getChildren().size();p++){
//			if(n3EditorDiagramModel.getChildren().get(p) instanceof FinalClassModel)
//			if(((FinalClassModel)n3EditorDiagramModel.getChildren().get(p)).getClassModel()==model.getClassModel() ){

//			Vector s=((FinalClassModel)n3EditorDiagramModel.getChildren().get(p)).getTargetConnections();
//			for(int k=0; k<s.size();k++){
//			if( s.get(k) instanceof LineModel){
//			LineModel lineModel=(LineModel)s.get(k);
//			for (int b = 0; b < s.size(); b++) {
//			LineModel wire = (LineModel)s.get(b);
//			wire.detachSource();
//			wire.detachTarget();
//			}
//			for (int b = 0; b < s.size(); b++) {
//			LineModel wire = (LineModel)s.get(b);
//			wire.detachSource();
//			wire.detachTarget();
//			}
//			}						
//			}
//			s=((FinalClassModel)n3EditorDiagramModel.getChildren().get(p)).getSourceConnections();
//			for(int k=0; k<s.size();k++){
//			if( s.get(k) instanceof LineModel){
//			LineModel lineModel=(LineModel)s.get(k);
//			for (int b = 0; b < s.size(); b++) {
//			LineModel wire = (LineModel)s.get(b);
//			wire.detachSource();
//			wire.detachTarget();
//			}
//			for (int b = 0; b < s.size(); b++) {
//			LineModel wire = (LineModel)s.get(b);
//			wire.detachSource();
//			wire.detachTarget();
//			}
//			}						
//			}

//			}
//			}
//			l.removeModel((UMLModel)model);
//			}
////			UMLDeleteCommand.deleteConnections(uMLModel);


//			}
			TreeSimpleFactory factory = new TreeSimpleFactory();
			value=(FinalClassModel)factory.createModle(model);
			value.getUMLTreeModel().addN3UMLModelDeleteListener(n3EditorDiagramModel);
			return value;
		}
		private void attachCellEditorsTag(final TableViewer viewer, Composite parent) {
			viewer.setCellModifier(
					new ICellModifier() {
						public boolean canModify(Object element, String property) {
							return true;
						}
						public Object getValue(Object element, String property) {
							if (PATTERN_PROPERTY1.equals(property))
								return ((DetailPropertyTableItem)element).tapName;
							else if (PATTERN_PROPERTY2.equals(property))
								return ((DetailPropertyTableItem)element).desc;

							return null;
						}
						public void modify(Object element, String property, Object value) {
							TableItem tableItem = (TableItem)element;
							DetailPropertyTableItem data = (DetailPropertyTableItem)tableItem.getData();
							if (PATTERN_PROPERTY1.equals(property)){
								data.tapName = value.toString();
							}
							else if (PATTERN_PROPERTY2.equals(property)){
								data.desc = value.toString();
							}
							viewer.refresh(data);
						}
					});
			
			viewer.setCellEditors(
					new CellEditor[] {
							null,new PatternCellEditor(parent)
					});
			viewer.setColumnProperties(
					new String[] {
							PATTERN_PROPERTY1,PATTERN_PROPERTY2
					});
		}
		
		public void createGeneralizeLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new GeneralizeLineModel();
			if (source != null) {
		        
	        	if(wire instanceof GeneralizeLineModel){
	        		if(source.getParentModel()!=null &&  target.getParentModel()!=null){
	        			source.getParentModel().getParents().add(target.getParentModel());
	        	
	        		}
	        		else if(source.getParentModel()==null && target.getParentModel()!=null){
	        			source.getParents().add(target.getParentModel());
	        		}
	        		else if(source.getParentModel()!=null && target.getParentModel()==null){
	        			source.getParentModel().getParents().add(target);
	        		}
	        		else if(source.getParentModel()==null && target.getParentModel()==null){
	        			source.getParents().add(target);
	        		}
	        	}
	        	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
		}
		public void createGeneralizeLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm,String value){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new GeneralizeLineModel();
			String[] data= value.split(",");
			Vector bend =new Vector();
			if (source != null) {
		        
	        	if(wire instanceof GeneralizeLineModel){
	        		if(source.getParentModel()!=null &&  target.getParentModel()!=null){
	        			source.getParentModel().getParents().add(target.getParentModel());
	        	
	        		}
	        		else if(source.getParentModel()==null && target.getParentModel()!=null){
	        			source.getParents().add(target.getParentModel());
	        		}
	        		else if(source.getParentModel()!=null && target.getParentModel()==null){
	        			source.getParentModel().getParents().add(target);
	        		}
	        		else if(source.getParentModel()==null && target.getParentModel()==null){
	        			source.getParents().add(target);
	        		}
	        	}
	        	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
			if(target != null&&source != null){
				if(data != null){
				for(int i = 0; i < data.length; i++){
					int keyIndex = data[i].indexOf("=");
					String type = data[i].substring(0,keyIndex);
					String desc = data[i].substring(keyIndex+1,data[i].length());

					if(type.equals(LineModel.ID_SOURCE_ROLE))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_ROLE, desc);
			
					else if(type.equals(LineModel.ID_SOURCE_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_TARGET_ROLE))						
						wire.getDetailProp().put(LineModel.ID_TARGET_ROLE, desc);

					else if(type.equals(LineModel.ID_TARGET_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_SOURCE_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_AGGREGATION, desc);

					else if(type.equals(LineModel.ID_TARGET_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_TARGET_AGGREGATION, desc);
					else if(type.equals(LineModel.ID_NAME)){
						wire.getDetailProp().put(LineModel.ID_NAME, desc);
					}
					else if(type.equals("Bendpoint")){
						String cut = desc.substring(1, desc.length()-1);
						String[] point = cut.split(":");
						LineBendpointModel lbm = new LineBendpointModel();
						float weight = 0;
						int w1 = 0;
						int h1 = 0;
						int w2 = 0;
						int h2 = 0;
						if(point.length==5){
							weight = Float.parseFloat(point[0]);
							w1 = Integer.parseInt(point[1]);
							h1 = Integer.parseInt(point[2]);
							w2 = Integer.parseInt(point[3]);
							h2 = Integer.parseInt(point[4]);
							lbm.setWeight(weight);
							lbm.setRelativeDimensions(new Dimension(w1,h1),new Dimension(w2,h2));
							bend.add(lbm);

						}
					}
					
				}
				if(bend.size()>0){
					wire.setBendpoints(bend);
				}
				if(data.length>0){
					
//					wire.setDetailProp(wire.getDetailProp());
					
				}
				}
			}
		}
		public void createAssociateLineModelModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new AssociateLineModel();
			if (source != null) {
		        
	        	
	        	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
		}
		public void createAssociateLineModelModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm,String value){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new AssociateLineModel();
			String[] data= value.split(",");
			Vector bend =new Vector();
			if (source != null) {    
				wire.detachSource();
				wire.setSource(source);
				wire.setSourceTerminal("B");
				wire.attachSource();
				

				
				
					wire.setDetailProp(wire.getDetailProp());
				
			}
			if (target != null) {
				wire.detachTarget();
				wire.setTarget(target);
				wire.setTargetTerminal("B");
				wire.attachTarget(ndm);
			
					
				
		
			}
			if(target != null&&source != null){
				if(data != null){
				for(int i = 0; i < data.length; i++){
					int keyIndex = data[i].indexOf("=");
					String type = data[i].substring(0,keyIndex);
					String desc = data[i].substring(keyIndex+1,data[i].length());

					if(type.equals(LineModel.ID_SOURCE_ROLE))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_ROLE, desc);
			
					else if(type.equals(LineModel.ID_SOURCE_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_TARGET_ROLE))						
						wire.getDetailProp().put(LineModel.ID_TARGET_ROLE, desc);

					else if(type.equals(LineModel.ID_TARGET_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_SOURCE_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_AGGREGATION, desc);

					else if(type.equals(LineModel.ID_TARGET_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_TARGET_AGGREGATION, desc);
					
					else if(type.equals(LineModel.ID_SOURCE_MUL))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_MUL, desc);
					
					else if(type.equals(LineModel.ID_TARGET_MUL))						
						wire.getDetailProp().put(LineModel.ID_TARGET_MUL, desc);
					else if(type.equals(LineModel.ID_NAME)){
						wire.getDetailProp().put(LineModel.ID_NAME, desc);
					}
					else if(type.equals("Bendpoint")){
						String cut = desc.substring(1, desc.length()-1);
						String[] point = cut.split(":");
						LineBendpointModel lbm = new LineBendpointModel();
						float weight = 0;
						int w1 = 0;
						int h1 = 0;
						int w2 = 0;
						int h2 = 0;
						if(point.length==5){
							weight = Float.parseFloat(point[0]);
							w1 = Integer.parseInt(point[1]);
							h1 = Integer.parseInt(point[2]);
							w2 = Integer.parseInt(point[3]);
							h2 = Integer.parseInt(point[4]);
							lbm.setWeight(weight);
							lbm.setRelativeDimensions(new Dimension(w1,h1),new Dimension(w2,h2));
							
							bend.add(lbm);
							wire.setBendpoints(bend);
						}
					}
					
				}
				if(bend.size()>0){
					wire.setBendpoints(bend);
				}
				if(data.length>0){
					
					wire.setDetailProp(wire.getDetailProp());
					
				}
				
				}
			}
		}
		
		public void createDependencyLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new DependencyLineModel();
			if (source != null) {
		        
	        	
	        	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
		}
		
		public void createDependencyLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm,String value){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new DependencyLineModel();
			Vector bend =new Vector();
			String[] data= value.split(",");
			if (source != null) {  
	        	
	        	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
			if(target != null&&source != null){
				if(data != null){
				for(int i = 0; i < data.length; i++){
					int keyIndex = data[i].indexOf("=");
					String type = data[i].substring(0,keyIndex);
					String desc = data[i].substring(keyIndex+1,data[i].length());

					if(type.equals(LineModel.ID_SOURCE_ROLE))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_ROLE, desc);
			
					else if(type.equals(LineModel.ID_SOURCE_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_TARGET_ROLE))						
						wire.getDetailProp().put(LineModel.ID_TARGET_ROLE, desc);

					else if(type.equals(LineModel.ID_TARGET_NAVIGABILITY))						
						wire.getDetailProp().put(LineModel.ID_TARGET_NAVIGABILITY, "Navigable");

					else if(type.equals(LineModel.ID_SOURCE_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_SOURCE_AGGREGATION, desc);

					else if(type.equals(LineModel.ID_TARGET_AGGREGATION))						
						wire.getDetailProp().put(LineModel.ID_TARGET_AGGREGATION, desc);
					else if(type.equals(LineModel.ID_NAME)){
						wire.getDetailProp().put(LineModel.ID_NAME, desc);
					}
					else if(type.equals("Bendpoint")){
						String cut = desc.substring(1, desc.length()-1);
						String[] point = cut.split(":");
						LineBendpointModel lbm = new LineBendpointModel();
						float weight = 0;
						int w1 = 0;
						int h1 = 0;
						int w2 = 0;
						int h2 = 0;
						if(point.length==5){
							weight = Float.parseFloat(point[0]);
							w1 = Integer.parseInt(point[1]);
							h1 = Integer.parseInt(point[2]);
							w2 = Integer.parseInt(point[3]);
							h2 = Integer.parseInt(point[4]);
							lbm.setWeight(weight);
							lbm.setRelativeDimensions(new Dimension(w1,h1),new Dimension(w2,h2));
							
							bend.add(lbm);
							wire.setBendpoints(bend);
						}
					}
					
				}
				if(bend.size()>0){
					wire.setBendpoints(bend);
				}
				if(data.length>0){
					
					wire.setDetailProp(wire.getDetailProp());
					
				}
				
				}
			}
		}
		
		public void createRealizeLineModel(UMLModel source,UMLModel target,N3EditorDiagramModel ndm){
			LineModel wire = null;
//			N3EditorDiagramModel ndm = null;
			wire=new RealizeLineModel();
			if (source != null) {
		              	
	            wire.detachSource();
	            wire.setSource(source);
	            wire.setSourceTerminal("B");
	            wire.attachSource();
	        }
	        if (target != null) {
	            wire.detachTarget();
	            wire.setTarget(target);
	            wire.setTargetTerminal("B");
	            wire.attachTarget(ndm);
	       
	           
	        }
		}
		
		public UMLTreeModel makeTreeModel(UMLModel um,N3EditorDiagramModel n3EditorDiagramModel){
	    	
   		 UMLTreeModel to1 = null;
   		UMLTreeParentModel up = (UMLTreeParentModel)n3EditorDiagramModel.getUMLTreeModel().getParent();
   		 if(up!=null){
   			String name = um.getName();
               to1 = new StrcuturedPackageTreeModel(name);//2008040203 PKY S 
               to1.setRefModel(um);
               um.setName(name);
               um.setTreeModel(to1);
               to1.setParent(up);
               up.addChild(to1);
               to1.addN3UMLModelDeleteListener(n3EditorDiagramModel);
               ProjectManager.getInstance().addTypeModel(to1);
               return up;
   		}
   		
   		
          return null;

   	
   }
		
		protected void buttonPressed(int buttonId) {
			if (buttonId == RESET_ID) {
				
			}
			else {
				if (buttonId == this.OK_ID) {
					this.definePatternDiagram(this.patternType,this.patternViewer.getTable().getItems());
				

					//				System.out.println("tableItem:"+tableItem[0].getData());
				}
				else{
//				
				}
			
				super.buttonPressed(buttonId);
			}
		}
	 
	 class TreeObject implements IAdaptable {
	        private String name;
	        private int type;
	        private TreeParent parent;

	        public TreeObject(String name) {
	            this.name = name;
	        }

	        public TreeObject(String name, int p) {
	            this.name = name;
	            this.type = p;
	        }

	        public String getName() {
	            return name;
	        }

	        public int getType() {
	            return type;
	        }

	        public void setParent(TreeParent parent) {
	            this.parent = parent;
	        }

	        public TreeParent getParent() {
	            return parent;
	        }

	        public String toString() {
	            return getName();
	        }

	        public Object getAdapter(Class key) {
	            return null;
	        }
	    }


	    class TreeParent extends TreeObject {
	        private ArrayList children;

	        public TreeParent(String name) {
	            super(name);
	            children = new ArrayList();
	        }

	        public TreeParent(String name, int p) {
	            super(name, p);
	            children = new ArrayList();
	        }

	        public void addChild(TreeObject child) {
	            children.add(child);
	            child.setParent(this);
	        }

	        public void removeChild(TreeObject child) {
	            children.remove(child);
	            child.setParent(null);
	        }

	        public TreeObject[] getChildren() {
	            return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
	        }

	        public boolean hasChildren() {
	            return children.size() > 0;
	        }
	    }


	    class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
	        //		private TreeParent invisibleRoot;
	        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	        }

	        public void dispose() {
	        }

	        public Object[] getElements(Object parent) {
	            //			if (invisibleRoot==null){ initialize();
	            //			return getChildren(invisibleRoot);
	            //			}
	            return getChildren(parent);
	        }

	        public Object getParent(Object child) {
	            if (child instanceof TreeObject) {
	                return ((TreeObject)child).getParent();
	            }
	            return null;
	        }

	        public Object[] getChildren(Object parent) {
	            if (parent instanceof TreeParent) {
	                return ((TreeParent)parent).getChildren();
	            }
	            return new Object[0];
	        }

	        public boolean hasChildren(Object parent) {
	            if (parent instanceof TreeParent)
	                return ((TreeParent)parent).hasChildren();
	            return false;
	        }
	    }


	    private TreeParent initialize() {
	        TreeParent ejb = new TreeParent(N3Messages.PATTERN_EJB, 229);
	        TreeParent tpe1 = new TreeParent(N3Messages.PATTERN_ENTITY_EJB, 230);//2008040301 PKY S
	        TreeParent tpe2 = new TreeParent(N3Messages.PATTERN_MESSAGEDRIVEN_EJB, 231);//2008040301 PKY S
	        TreeParent tpe3 = new TreeParent(N3Messages.PATTERN_SESSION_EJB, 232);//2008040301 PKY S
	        ejb.addChild(tpe1);
	        ejb.addChild(tpe2);
	        ejb.addChild(tpe3);
	        
	        TreeParent gof = new TreeParent(N3Messages.PATTERN_GOF, 205);//2008040301 PKY S
	        TreeParent tpg1 = new TreeParent(N3Messages.PATTERN_ABSTRACT_FACTORY, 206);//2008040301 PKY S
	        TreeParent tpg2 = new TreeParent(N3Messages.PATTERN_ADAPTER, 207);//2008040301 PKY S
	        TreeParent tpg3 = new TreeParent(N3Messages.PATTERN_BRIDGE, 208);//2008040301 PKY S
	        TreeParent tpg4 = new TreeParent(N3Messages.PATTERN_BUILDER, 209);//2008040301 PKY S
	        TreeParent tpg5 = new TreeParent(N3Messages.PATTERN_CHAIN_OF_REPONSIBILITY, 210);//2008040301 PKY S
	        TreeParent tpg6 = new TreeParent(N3Messages.PATTERN_COMMAND, 211);//2008040301 PKY S
	        TreeParent tpg7 = new TreeParent(N3Messages.PATTERN_COMPOSITE, 212);//2008040301 PKY S
	        TreeParent tpg8 = new TreeParent(N3Messages.PATTERN_DECORATOR, 213);//2008040301 PKY S
	        TreeParent tpg9 = new TreeParent(N3Messages.PATTERN_FACADE, 214);//2008040301 PKY S
	        TreeParent tpg10 = new TreeParent(N3Messages.PATTERN_FACTORY_METHOD, 215);//2008040301 PKY S
	        TreeParent tpg11 = new TreeParent(N3Messages.PATTERN_FLYWEIGHT, 216);//2008040301 PKY S
	        TreeParent tpg12 = new TreeParent(N3Messages.PATTERN_INTERPRETER, 217);//2008040301 PKY S
	        TreeParent tpg13 = new TreeParent(N3Messages.PATTERN_ITERATOR, 218);//2008040301 PKY S
	        TreeParent tpg14 = new TreeParent(N3Messages.PATTERN_MEDIATOR, 219);//2008040301 PKY S
	        TreeParent tpg15 = new TreeParent(N3Messages.PATTERN_MEMENTO, 220);//2008040301 PKY S
	        TreeParent tpg16 = new TreeParent(N3Messages.PATTERN_OBSERVER, 221);//2008040301 PKY S
	        TreeParent tpg17 = new TreeParent(N3Messages.PATTERN_PROTOTYPE, 222);//2008040301 PKY S
	        TreeParent tpg18 = new TreeParent(N3Messages.PATTERN_PROXY, 223);//2008040301 PKY S
	        TreeParent tpg19 = new TreeParent(N3Messages.PATTERN_SINGLETON, 224);//2008040301 PKY S
	        TreeParent tpg20 = new TreeParent(N3Messages.PATTERN_STATE, 225);//2008040301 PKY S
	        TreeParent tpg21 = new TreeParent(N3Messages.PATTERN_STRATEGY, 226);//2008040301 PKY S
	        TreeParent tpg22 = new TreeParent(N3Messages.PATTERN_TEMPLATE_METHOD, 227);//2008040301 PKY S
	        TreeParent tpg23 = new TreeParent(N3Messages.PATTERN_VISITOR, 228);//2008040301 PKY S

	        
	        
	        
	        gof.addChild(tpg1);
	        gof.addChild(tpg2);
	        gof.addChild(tpg3);
	        gof.addChild(tpg4);
	        gof.addChild(tpg5);
	        gof.addChild(tpg6);
	        gof.addChild(tpg7);
	        gof.addChild(tpg8);
	        gof.addChild(tpg9);
	        gof.addChild(tpg10);
	        gof.addChild(tpg11);
	        gof.addChild(tpg12);
	        gof.addChild(tpg13);
	        gof.addChild(tpg14);
	        gof.addChild(tpg15);
	        gof.addChild(tpg16);
	        gof.addChild(tpg17);
	        gof.addChild(tpg18);
	        gof.addChild(tpg19);
	        gof.addChild(tpg20);
	        gof.addChild(tpg21);
	        gof.addChild(tpg22);
	        gof.addChild(tpg23);

	        
	        
	        TreeParent root = new TreeParent("PatternRepository");
	        root.addChild(ejb);
	        root.addChild(gof);
	        return root;
	    }

	    class ViewLabelProvider extends LabelProvider {
	        public String getText(Object obj) {
	            return obj.toString();
	        }

	        public Image getImage(Object obj) {
	            String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
	            if(obj instanceof TreeParent){
	            	TreeParent tp = (TreeParent)obj;
	            	return ProjectManager.getInstance().getImage(tp.getType());
	            }

	            return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	        }
	    }


	    class NameSorter extends ViewerSorter {
	    }
}
