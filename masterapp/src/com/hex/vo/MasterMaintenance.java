package de.dl.ucs.ui.batch;

import java.text.DateFormat ;
import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.Date ;
import java.util.HashMap ;
import java.util.Hashtable ;
import java.util.Iterator ;
import java.util.Set ;
import java.util.TreeSet ;
import java.util.Vector ;

import java.awt.Color ;
import java.awt.Component;
import java.awt.Dimension ;
import java.awt.GridBagConstraints ;
import java.awt.GridBagLayout ;
import java.awt.Insets ;
import java.awt.event.ActionEvent ;
import java.awt.event.FocusAdapter ;
import java.awt.event.FocusEvent ;
import java.awt.event.FocusListener ;
import java.awt.event.KeyEvent ;
import java.awt.event.KeyListener ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;

import javax.swing.BorderFactory ;
//import javax.swing.JLabel ;
//import javax.swing.JScrollPane ;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder ;
import javax.swing.event.InternalFrameEvent ;
import javax.swing.table.TableCellEditor ;
import javax.swing.table.TableCellRenderer ;

import common.fe.HexAnyText ;
import common.fe.HexButton ;
import common.fe.HexClearHandler ;
import common.fe.HexComboBox ;
import common.fe.HexDate ;
import common.fe.HexFont ;
import common.fe.HexInteger ;
import common.fe.HexInternalFrame ;
import common.fe.HexMessageManager ;
import common.fe.HexNumRange ;
import common.fe.HexOLabel ;
import common.fe.HexOkBtn ;
import common.fe.HexOkHandler ;
import common.fe.HexPanel ;
import common.fe.HexTableView ;
import common.fe.HexTextArea ;
import common.fe.Language ;
import common.fe.UCSFocusTraversalPolicy;

import de.dl.ucs.framework.clientclasses.UCSLogger ;
import de.dl.ucs.framework.clientclasses.UIController ;
import de.dl.ucs.framework.flowcontroller.TransferObject ;
import de.dl.ucs.framework.utils.UCSConcurrency ;
import de.dl.ucs.framework.utils.UCSUtil ;

import de.dl.ucs.batch.vo.FunctionListVO ;
import de.dl.ucs.batch.vo.ParameterMasterVO ;
import de.dl.ucs.batch.vo.ParameterTxVO ;
import de.dl.ucs.batch.vo.ProgramListVO ;

import pv.jfcx.JPVDate ;
import pv.jfcx.JPVDatePlus ;
import pv.jfcx.JPVTable ;
import pv.jfcx.PVTableModel ;




public class MasterMaintenance extends HexInternalFrame {
    UCSLogger ioLogger = new UCSLogger("MasterMaintenance");
    HexOLabel statusBar = new HexOLabel();
    HexOLabel lblToolTipText = new HexOLabel();
    HexMessageManager resources;
    HexPanel contentPane;
    HexPanel pnlButton = new HexPanel();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    HexClearHandler clearHandler = new HexClearHandler();
    HexButton btnCancel = new HexButton("BAT_EI_CANCEL", "N", "", this);
    HexButton btnClear = new HexButton("BAT_EI_CLEAR", "N", "", this);
    HexButton btnRestDflt = new HexButton("BAT_EI_RESTORE_DEFAULT", "Y", "",
	    this);
    pv.jfcx.JPVEdit ParamName = new pv.jfcx.JPVEdit();
    pv.jfcx.JPVEdit ParamValue = new pv.jfcx.JPVEdit();
    pv.jfcx.JPVEdit TypeFlag = new pv.jfcx.JPVEdit();
    int modflag = 0;
    HexPanel pnlZAnlegen = new HexPanel("BAT_EI_MAIN_PANEL");
    HexPanel pnlToolTip = new HexPanel("BAT_EI_TOOLTIP_PANEL");
    HexComboBox cmbCategory = new HexComboBox("BAT_EI_CMB_CATEGORY", this);
    HexOLabel lblCategory = new HexOLabel();
    HexComboBox cmbProgramName = new HexComboBox("BAT_EI_CMB_PROGRAM_NAME", this);
    HexOLabel lblProgramName = new HexOLabel();
    HexComboBox cmbStepID = new HexComboBox("BAT_EI_CMB_STEPID", this);
    HexOLabel lblStepID = new HexOLabel();
    HexAnyText txtModuleID = new HexAnyText("BAT_EI_MODULEID", this);
    HexOLabel lblModuleID = new HexOLabel();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    int lnTableWidth;
    int lnTableHeight;
    boolean ibFlag = false;
    HexNumRange[] ioRangeArray;
    HexInteger[]  ioIntArray;
    String[] lsToolTipText = null;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout4 = new GridBagLayout();
    HexOkBtn btnSaveChanges = new HexOkBtn("BAT_EI_SAVE", "Y", "", this);
    HexOkHandler okHandler = new HexOkHandler();
    GridBagLayout gridBagLayout5 = new GridBagLayout();
    GridBagLayout gridBagLayout6 = new GridBagLayout();
    HexOLabel lblPageCount = new HexOLabel();
    HexOkBtn btnModify = new HexOkBtn("BAT_EI_BTN_MODIFY", "Y", "", this);//13740
    HexOkBtn btnFetch = new HexOkBtn("BAT_EI_BTNFETCH", "N", "", this);//13740
    HexOLabel lblProcessDate = new HexOLabel();
    HexDate dtProcessDate = new HexDate("BAT_EI_PROCESS_DATE", this);
    HexTableView lotableView = null;
    PVTableModel model = null;
    Vector loColumnNames = null;
    Vector loData = null;
    HashMap loFunctionListVO = null;
    FunctionListVO loFunctionVO = null;
    Date dtProcessDateFromdb = null;
    private ParameterTxVO[] ioParameterTXVO = null;
    //private String isModeFlag = "LOAD";
    int lnCount = 1;
    int lnCountInt = 1;
    HexTextArea txtToolTip= new HexTextArea("BAT_EI_TOOL_TIP",this);
    UCSConcurrency loGlobalUCSConcurrency= null;
    /**Construct the frame*/
    public MasterMaintenance() {
	try {
	    resources = this.getResources(Language.getSelectedLanguage());
	    tableSettings();
	    jbInit();

	    statusBar.setText("    ");
	    lblToolTipText.setText("    ");
	    setClearHandler();
	    this.pack();
	    this.setVisible(true);
	    this.setScreenId("BAT001");
	    getData();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**Component initialization*/
    private void jbInit() //throws Exception 
    {
	ioLogger.debug("jbinit()");
	contentPane = (HexPanel) this.getContentPane();
	titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(
		    Color.white, new Color(148, 145, 140)), "");
	titledBorder1.setTitle(resources.getString("List"));
	titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(
		    Color.white, new Color(148, 145, 140)), "");
    //Modified by Aiswaria for the SIT defect 12106
	titledBorder2.setTitle(resources.getString("SelectMasterMaint"));
	titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(
			Color.white, new Color(148, 145, 140)), "");
	    titledBorder3.setTitle(resources.getString("Note"));
	btnSaveChanges.setEnabled(false);
	btnModify.setEnabled(false);
	btnRestDflt.setEnabled(false);
	btnClear.setEnabled(false);
	contentPane.setLayout(gridBagLayout4);
	this.setDefaultButton(btnCancel);
	cmbProgramName.setPreferredSize(new Dimension(167,23));
	cmbStepID.setPreferredSize(new Dimension(167,23));
	cmbCategory.setPreferredSize(new Dimension(167,23));

	this.addEventsListener(new javax.swing.event.InternalFrameListener() {
		public void internalFrameOpened(InternalFrameEvent e) {
		}

		public void internalFrameClosing(InternalFrameEvent e) {
		}

		public void internalFrameClosed(InternalFrameEvent e) {
		}

		public void internalFrameIconified(InternalFrameEvent e) {
		}

		public void internalFrameDeiconified(InternalFrameEvent e) {
		}

		public void internalFrameActivated(InternalFrameEvent e) {
		    this_internalFrameActivated(e);
		}

		public void internalFrameDeactivated(InternalFrameEvent e) {
		}
	    });

	this.setTitle(resources.getString("BAT001"));
	btnCancel.setText(resources.getString("Cancel"));
	btnCancel.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnCancel_actionPerformed(e);
		}
	    });
	pnlButton.setLayout(gridBagLayout2);
	btnClear.setText(resources.getString("Clear"));
	btnClear.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnClear_actionPerformed(e);
		}
	    });

	btnModify.setText(resources.getString("Modify"));
	btnModify.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnModify_actionPerformed(e);
		}
	    });

	btnFetch.setText(resources.getString("Fetch"));
	btnFetch.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnFetch_actionPerformed(e);
		}
	    });
	btnRestDflt.setText(resources.getString("Restore_Default"));
	btnRestDflt.setMnemonic('t');//13740
	btnRestDflt.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnRestDflt_actionPerformed(e);
		}
	    });

	cmbCategory.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    cmbCategory_actionPerformed();
		}
	    });

	cmbProgramName.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    cmbProgramName_actionPerformed();
		}
	    });
	cmbStepID.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    cmbStepID_actionPerformed();
		}
	    });
	txtModuleID.setEnabled(false);
	btnSaveChanges.setText(resources.getString("Save"));
	btnSaveChanges.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    btnSaveChanges_actionPerformed(e);
		}
	    });
	dtProcessDate.addFocusListener(new FocusAdapter() {
		public void focusLost(FocusEvent e) {
		    dtProcessDate_focusLost();
		}
	    });
	lotableView.getTable().addMouseListener(new MouseListener() {
		public void mouseClicked(MouseEvent e) {
		    jTableView_MouseClicked();
		}

		public void mouseReleased(MouseEvent e) {
		    for (int i = 0; i < model.getRowCount(); i++) {
			if (lotableView.getValueAt(i, 2).toString().equals("NR") &&
				(lotableView.getValueAt(i, 3) != null)) {
			    int lntemp = Integer.parseInt(lotableView.getValueAt(
					i, 3).toString());
			    lotableView.setValueAt(new String(
				    ioRangeArray[lntemp].getText()), i, 1);
			    ioRangeArray[lntemp].repaint();
			}
			if (lotableView.getValueAt(i, 2).toString().equals("N") &&
					(lotableView.getValueAt(i, 3) != null)) {
				    int lntemp = Integer.parseInt(lotableView.getValueAt(
						i, 3).toString());
				    lotableView.setValueAt(new String(
						    ioIntArray[lntemp].getText()), i, 1);
				    ioIntArray[lntemp].repaint();
				}
		    }
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}
	    });

	lotableView.getTable().addFocusListener(new FocusAdapter() {
		public void focusLost(FocusEvent e) {
		    jTableView_FocusLost();
		}
	    });

	lotableView.getTable().addKeyListener(new KeyListener() {
		public void keyReleased(KeyEvent e) {
		    for (int i = 0; i < model.getRowCount(); i++) {
			if (lotableView.getValueAt(i, 2).toString().equals("NR") &&
				(lotableView.getValueAt(i, 3) != null)) {
			    int lntemp = Integer.parseInt(lotableView.getValueAt(
					i, 3).toString());
			    lotableView.setValueAt(new String(
				    ioRangeArray[lntemp].getText()), i, 1);
			    ioRangeArray[lntemp].repaint();
			}
			if (lotableView.getValueAt(i, 2).toString().equals("N") &&
					(lotableView.getValueAt(i, 3) != null)) {
				    int lntemp = Integer.parseInt(lotableView.getValueAt(
						i, 3).toString());
				    lotableView.setValueAt(new String(
					    ioIntArray[lntemp].getText()), i, 1);
				    ioIntArray[lntemp].repaint();
				}
		    }
		}

		public void keyPressed(KeyEvent e) {
		    if (e.getKeyChar() == KeyEvent.VK_TAB) {
			int row = lotableView.getTable().getEditingRow();
			lotableView.setFocusCell(row + 1, 2);
		    } else if (e.getKeyChar() == (KeyEvent.VK_TAB +
			    KeyEvent.VK_SHIFT)) {
			int row = lotableView.getTable().getEditingRow();
			lotableView.setFocusCell(row - 1, 2);
		    }
		}

		public void keyTyped(KeyEvent e) {
		}
	    });

	txtToolTip.setColumns(15);
	txtToolTip.setLineWrap(true);
	txtToolTip.setRows(9);
	txtToolTip.setMaxLength(300);
	txtToolTip.setKey(" ");
	txtToolTip.setEnabled(false);
	txtToolTip.setBorder(titledBorder3);
	lblCategory.setText(resources.getString("Category"));
	lblProgramName.setText(resources.getString("ProgramName"));
	lblStepID.setText(resources.getString("StepID"));
	lblModuleID.setText(resources.getString("ModuleID"));
	txtModuleID.setMaxLength(50);//internal defect
	txtModuleID.setPreferredSize(new Dimension(167,23));
	lblProcessDate.setText(resources.getString("ProcessDate"));
	pnlZAnlegen.setLayout(gridBagLayout3);
	pnlToolTip.setLayout(gridBagLayout1);
	pnlZAnlegen.setBorder(titledBorder2);
	pnlToolTip.setBorder(titledBorder1);

	pnlButton.add(btnModify,
	    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.EAST, GridBagConstraints.NONE,
		new Insets(0, 0, 11, 5), 0, 0));
	pnlButton.add(btnSaveChanges,
	    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.EAST, GridBagConstraints.NONE,
		new Insets(0, 0, 11, 5), 0, 0));
	pnlButton.add(btnClear,
	    new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.EAST, GridBagConstraints.NONE,
		new Insets(0, 0, 11, 5), 0, 0));
	pnlButton.add(btnCancel,
	    new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
		GridBagConstraints.EAST, GridBagConstraints.NONE,
		new Insets(0, 0, 11, 5), 0, 0));

	pnlZAnlegen.add(lblProcessDate,
	    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));
	pnlZAnlegen.add(dtProcessDate,
	    new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));
	pnlZAnlegen.add(lblCategory,
	    new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(0, 12, 12, 0), 0, 0));
	pnlZAnlegen.add(cmbCategory,
	    new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));

	//2
	pnlZAnlegen.add(lblProgramName,
	    new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(0, 12, 12, 0), 0, 0));
	pnlZAnlegen.add(cmbProgramName,
	    new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));

	//3
	pnlZAnlegen.add(lblStepID,
	    new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(0, 12, 12, 0), 0, 0));
	pnlZAnlegen.add(cmbStepID,
	    new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));

	//4
	pnlZAnlegen.add(lblModuleID,
	    new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(0, 12, 12, 0), 0, 0));
	pnlZAnlegen.add(txtModuleID,
	    new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));

	pnlZAnlegen.add(btnFetch,
	    new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));
	pnlZAnlegen.add(btnRestDflt,
	    new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
		GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
		new Insets(5, 12, 12, 11), 0, 0));

	pnlToolTip.add(lotableView,
				 new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
	    ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 12, 11, 0), 0, 0));
	pnlToolTip.add(txtToolTip,
				new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				    GridBagConstraints.EAST, GridBagConstraints.NONE,
				    new Insets(5, 12, 0, 11), 0, 0));


//	contentPane.add(lotableView,
//	    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
//		GridBagConstraints.WEST, GridBagConstraints.NONE,
//		new Insets(11, 12, 11, 11), 0, 0));

	contentPane.add(statusBar,
	    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
		GridBagConstraints.WEST, GridBagConstraints.NONE,
		new Insets(0, 11, 0, 0), 0, 0));
	contentPane.add(pnlZAnlegen,
			    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(12, 12, 0, 0), 0, 0));
	contentPane.add(pnlToolTip,
					   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
	    ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 12, 0, 11), 0, 0));
    contentPane.add(pnlButton,
					 new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
	    ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(17, 0, 0, 0), 14, 0));
//        contentPane.add(lblToolTipText,
//                        new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
//                            GridBagConstraints.WEST, GridBagConstraints.NONE,
//                            new Insets(0, 11, 0, 0), 0, 0));

	loFunctionListVO = null;
	loFunctionVO = null;	
	//btnCancel.requestDefaultFocus();
	Vector<Component> order = new Vector<Component>(12);
	order.add(dtProcessDate);
	order.add(cmbCategory);
	order.add(cmbProgramName);
	order.add(cmbStepID);
	order.add(txtModuleID);
	order.add(btnFetch);
	order.add(btnRestDflt);	
	order.add(lotableView);
	order.add(btnModify);
	order.add(btnSaveChanges);
	order.add(btnClear);
	order.add(btnCancel);
	UCSFocusTraversalPolicy newPolicy = new UCSFocusTraversalPolicy(order);
	setFocusTraversalPolicy(newPolicy);

    }

    void jTableView_MouseClicked() {
	ioLogger.debug("jTableView_MouseClicked()");

	if (ibFlag == false) {
	    lotableView.setEnabled(false);
	    lotableView.setEditable(false);
	    lotableView.setTableBackground(Color.lightGray);
	    lotableView.getTable().setCellSelectionEnabled(false);
	} else {
	    btnFetch.setEnabled(true);
	    lotableView.setEnabled(true);
	    lotableView.setEditable(true);
	    lotableView.setLockedColumns(0);
	    btnSaveChanges.setEnabled(true);
	}
    }

    void jTableView_FocusLost() {
	ioLogger.debug("jTableView_FocusLost()");
	lotableView.getTable().clearSelection();

	for (int i = 0; i < model.getRowCount(); i++) {
	    if (lotableView.getValueAt(i, 2).toString().equals("NR") &&
		    (lotableView.getValueAt(i, 3) != null)) {
		int lntemp = Integer.parseInt(lotableView.getValueAt(i, 3)
							 .toString());
		lotableView.setValueAt(new String(
			ioRangeArray[lntemp].getText()), i, 1);
		ioRangeArray[lntemp].repaint();
	    }
	    if (lotableView.getValueAt(i, 2).toString().equals("N") &&
			    (lotableView.getValueAt(i, 3) != null)) {
			int lntemp = Integer.parseInt(lotableView.getValueAt(i, 3)
								 .toString());
			lotableView.setValueAt(new String(
				ioIntArray[lntemp].getText()), i, 1);
			ioIntArray[lntemp].repaint();
		    }
	}
    }

    void this_internalFrameActivated(InternalFrameEvent e) {
	btnFetch.setEnabled(false);
	btnModify.setEnabled(false);
    }

    void dtProcessDate_focusLost() {
	ioLogger.debug("dtProcessDate_focusLost()");

	if (((dtProcessDate.getDate() != null) &&
		dtProcessDate.getDate().before(dtProcessDateFromdb))) {
	    ioLogger.info("Date cannot be earlier than current date:BAT_E_0001");
	    //BAT_E_0001: Date cannot be earlier than current date
	    UIController.getInstance().showMessage("BAT_E_0001", this);
	    dtProcessDate.setText(UCSUtil.dateToString(dtProcessDateFromdb));
	    dtProcessDate.requestFocus();
	}
    }

    private void cmbCategory_actionPerformed() {
	ioLogger.debug("cmbCategory_actionPerformed()");

	String lsFunctionId = (String) cmbCategory.getSelectedItem();

	if ((lsFunctionId != null) && lsFunctionId.trim().equals("")) {
	    cmbStepID.removeAllItems();
	    cmbProgramName.removeAllItems();
	    cmbProgramName.addItem("                               ");
	    cmbStepID.addItem("                                     ");
	    btnFetch.setEnabled(false);
	    btnSaveChanges.setEnabled(false);
	    btnRestDflt.setEnabled(false);
	    model.setData(null);
	    btnModify.setEnabled(false);
	    btnClear.setEnabled(false);
	    statusBar.setText("");
		txtToolTip.setText("");
	    return;
	}

	loFunctionVO = (FunctionListVO) loFunctionListVO.get(lsFunctionId);

	String[] lsProgrameNames = loFunctionVO.getProgramNames();
	//Added by Venkat for UAT defect 13741 on Jan 31st 2006
	String[] lsSortedProgramNames=sortArray(lsProgrameNames);
	int length = lsProgrameNames.length;

	cmbProgramName.removeAllItems();
	cmbStepID.removeAllItems();

	txtModuleID.doClear();
	cmbProgramName.addItem("                                                  ");
	cmbStepID.addItem("                                                      ");
       for (int i = 0; i < length; i++) {
	    cmbProgramName.addItem(lsSortedProgramNames[i]);//13741
	}

	cmbProgramName.setSelectedIndex(0);
	btnFetch.setEnabled(false);
	btnRestDflt.setEnabled(false);
	statusBar.setText("");
	txtToolTip.setText("");
    }

    private void cmbProgramName_actionPerformed() {
	ioLogger.debug("cmbProgramName_actionPerformed()");

	String lsProgrameName = (String) cmbProgramName.getSelectedItem();

	if ((lsProgrameName == null) || lsProgrameName.trim().equals("")) {
	    cmbStepID.removeAllItems();
	    cmbStepID.addItem("                ");
	    model.setData(null);
	    btnSaveChanges.setEnabled(false);
	    btnModify.setEnabled(false);
	    statusBar.setText("");
		txtToolTip.setText("");
	} else {
	    ProgramListVO loProgramListVO = loFunctionVO.getProgramListVObyName(lsProgrameName);

	    cmbStepID.removeAllItems();
	    txtModuleID.doClear();
	    txtModuleID.setText(loProgramListVO.getModuleId());

	    String[] lsStepIds = loProgramListVO.getStepIDs();	    
	    String[] lsSortedStepIds= sortArray(lsStepIds);//13741
	    int length = lsStepIds.length;
	    cmbStepID.addItem("   ");

	    for (int i = 0; i < length; i++) {
		cmbStepID.addItem(lsSortedStepIds[i]);//13741
	    }

	    cmbStepID.setSelectedIndex(0);
	    btnFetch.setEnabled(false);
	    btnRestDflt.setEnabled(false);
	    statusBar.setText("");
		txtToolTip.setText("");
	}
    }

//    private void cmbCategory_itemStateChanged() {
//	ioLogger.debug("cmbCategory_itemStateChanged()");
//
//	String lsFunctionId = (String) cmbCategory.getSelectedItem();
//	ioLogger.debug("lsFunctionId" + lsFunctionId);
//	loFunctionVO = (FunctionListVO) loFunctionListVO.get(lsFunctionId);
//
//	String[] lsProgrameNames = loFunctionVO.getProgramNames();
//	cmbProgramName.removeAllItems();
//	cmbStepID.removeAllItems();
//
//	cmbProgramName.setItems(lsProgrameNames);
//	btnFetch.setEnabled(false);
//	btnRestDflt.setEnabled(false);
//    }

//    private void cmbProgramName_itemStateChanged() {
//	ioLogger.debug("cmbProgramName_itemStateChanged()");
//
//	String lsProgrameName = (String) cmbProgramName.getSelectedItem();
//
//	if (lsProgrameName.trim().equals("") || (lsProgrameName == null)) {
//	    cmbStepID.removeAllItems();
//	    model.setData(null);
//	    btnSaveChanges.setEnabled(false);
//	    btnModify.setEnabled(false);
//	} else {
//	    ProgramListVO loProgramListVO = loFunctionVO.getProgramListVObyName(lsProgrameName);
//	    String[] lsStepIds = loProgramListVO.getStepIDs();
//	    cmbStepID.setItems(lsStepIds);
//	}
//
//	btnFetch.setEnabled(false);
//	btnRestDflt.setEnabled(false);
//    }

    private void cmbStepID_actionPerformed() {
	ioLogger.debug("cmbStepID_actionPerformed()");

	String lsStepId = (String) cmbStepID.getSelectedItem();

	if ((lsStepId == null) || lsStepId.trim().equals("")) {
	    btnFetch.setEnabled(false);
	    btnRestDflt.setEnabled(false);
	    btnSaveChanges.setEnabled(false);
	    btnModify.setEnabled(false);
	    btnClear.setEnabled(false);
	    model.setData(null);
	} else {
	    btnFetch.setEnabled(true);
	    btnRestDflt.setEnabled(true);
	}
    }

    void btnCancel_actionPerformed(ActionEvent e) {
	ioLogger.debug("btnCancel_actionPerformed()");

	try {
	    this.fireClosing();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    void btnModify_actionPerformed(ActionEvent e) {
	ioLogger.debug("btnModify_actionPerformed()");
	btnModify.setEnabled(false);
	btnSaveChanges.setEnabled(true);
	ParamName.setEditable(false);
	ParamValue.setEditable(true);
	ParamName.setEnabled(false);
	lotableView.setColumnBackground(1, Color.white);
	lotableView.setFocusCell(0, 1);
	lotableView.requestFocus();
	btnClear.setEnabled(true);//13740
	modflag = 1;

	if (lnCount > 1) {
	    for (int i = 1; i < (lnCount - 1); i++) {
		ioRangeArray[i].setEditable(true);
	    }
	}
	if (lnCountInt > 1) {
	    for (int i = 1; i < (lnCountInt - 1); i++) {
		ioIntArray[i].setEditable(true);
	    }
	}
	//isModeFlag = "MODIFY";
	ibFlag = true;

	Vector table = model.getDataVector();

	for (int i = 0; i < table.size(); i++) {
	    Vector row = (Vector) table.get(i);
	    ioLogger.info("Row[" + i + "] " + row.toString());
	}
    } //btnModifyActionPerformed

    void btnFetch_actionPerformed(ActionEvent e) {
	ioLogger.debug("btnFetch_actionPerformed()");
	lotableView.getTable().clearSelection();
	txtToolTip.setText(" ");
	statusBar.setText("");
	lotableView.getTable().setCellSelectionEnabled(false);

	for (int i = 0; i < (int) model.getRowCount(); i++) {
	    lotableView.setValueAt(null, i, i);
	}

	ioLogger.debug("lnCount: " + lnCount);

	if (lnCount > 1) {
	    for (int i = 1; i < (lnCount - 1); i++) {
		ioRangeArray[i].setEditable(false);
	    }
	}
	if (lnCountInt > 1) {
	    for (int i = 1; i < (lnCountInt - 1); i++) {
		ioIntArray[i].setEditable(false);
	    }
	}
	model.setData(null);
	lotableView.setEnabled(false);
	statusBar.requestFocus();
	btnRestDflt.setEnabled(true);
	btnModify.setEnabled(true);
	btnClear.setEnabled(false);//13740
	//isModeFlag = "LOAD";
	ibFlag = false;

	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	String lsFunctionId = (String) cmbCategory.getSelectedItem();
	String lsProgrameName = (String) cmbProgramName.getSelectedItem();
	String lsStepId = (String) cmbStepID.getSelectedItem();
	String lsModuleId = txtModuleID.getText();
	Date ldProcessDate = (Date) dtProcessDate.getDate();
	String lsProcessDate = df.format(ldProcessDate);

	ioLogger.debug("ProcessDate: " + lsProcessDate);

	ParameterMasterVO loParamVO = new ParameterMasterVO();

	loParamVO.setFunctionId(lsFunctionId.trim());
	loParamVO.setProgramName(lsProgrameName.trim());
	loParamVO.setStepId(lsStepId.trim());
	loParamVO.setProcessDate(lsProcessDate);

	if (lsModuleId != null) {
	    loParamVO.setModuleId(lsModuleId.trim());
	}

	ParamValue.setEditable(false);
	model.setData(null);
	lotableView.setColumnBackground(1, Color.lightGray);

	TransferObject loTransferObject = new TransferObject();
	loTransferObject.setActionID("BAT_A_APX_PARAMETER");
	loTransferObject.setSubEventID("BAT_SE_FETCH");
	loTransferObject.setRequestData(loParamVO);

	UIController.getInstance().delegateActionToServer(loTransferObject,
	    this, true);
	btnModify.requestFocus();
    } //btnFetch ActionPerformed

    void btnClear_actionPerformed(ActionEvent e) {
	int rowCount;
	btnModify.setEnabled(true);
	btnSaveChanges.setEnabled(false);
	rowCount = model.getRowCount();

	for (int i = 0; i < rowCount; i++) {
	    model.setValueAt("", i, 1);
	}

	if (lnCount > 1  && lnCountInt >1) {
	    for (int i = 1; i < lnCount-1; i++) {
		ioRangeArray[i].setText("");
	    }
	    for (int i = 1; i < lnCountInt-1; i++) {
		ioIntArray[i].setText("");
	    }
	}

	btnFetch.requestFocus();
	btnSaveChanges.setEnabled(false);
    }

    void btnRestDflt_actionPerformed(ActionEvent e) {
	btnRestDflt.setEnabled(false);
	btnSaveChanges.setEnabled(true);
	btnModify.setEnabled(false);
	btnClear.setEnabled(false);
	model.setData(null);

	String lsFunctionId = (String) cmbCategory.getSelectedItem();
	String lsProgrameName = (String) cmbProgramName.getSelectedItem();
	String lsStepId = (String) cmbStepID.getSelectedItem();
	String lsModuleId = txtModuleID.getText();

	ParameterMasterVO loParamVO = new ParameterMasterVO();

	loParamVO.setFunctionId(lsFunctionId.trim());
	loParamVO.setProgramName(lsProgrameName.trim());
	loParamVO.setStepId(lsStepId.trim());
	loParamVO.setProcessDate(UCSUtil.dateToString(dtProcessDate.getDate()));
	loParamVO.setConcurrencyObject(loGlobalUCSConcurrency);

	if (lsModuleId != null) {
	    loParamVO.setModuleId(lsModuleId.trim());
	}

	model.setData(null);

	TransferObject loTransferObject = new TransferObject();
	loTransferObject.setActionID("BAT_A_APX_PARAMETER");
	loTransferObject.setSubEventID("BAT_SE_RESTORE_DEFAULT");
	loTransferObject.setRequestData(loParamVO);
	UIController.getInstance().delegateActionToServer(loTransferObject,
	    this, true);
    } //btnRestoreDefaultActionPerformed

    boolean checkTableDataChanged(ParameterTxVO[] loParameterTXVO) {
	boolean lbDataChanged=false;
	for (int i = 0; i <ioParameterTXVO.length ; i++) {
	    if(!ioParameterTXVO[i].getParameterValue().equals(loParameterTXVO[i].getParameterValue())) {
		lbDataChanged=true;
	    }
	}
	ioLogger.debug("DataChanged: "+lbDataChanged);
	return lbDataChanged;
    }

    void btnSaveChanges_actionPerformed(ActionEvent e) {
	statusBar.setText("   ");
	txtToolTip.setText("   ");
	int rowCount;
	TransferObject loTransferObject = new TransferObject();
	btnModify.setEnabled(true);
	ParamValue.setEditable(false);
	rowCount = model.getRowCount();

	String lsFunctionId = ((String) cmbCategory.getSelectedItem()).trim();
	String lsProgrameName = ((String) cmbProgramName.getSelectedItem()).trim();
	String lsStepId = ((String) cmbStepID.getSelectedItem()).trim();
	String lsModuleId = txtModuleID.getText().trim();
	String lsParameterId = (String) lsProgrameName + lsStepId + lsModuleId +
	    lsFunctionId;
	Date dtTemp = ((Date) dtProcessDate.getDate() == null) ? null
							       : (Date) dtProcessDate.getDate();

	ParameterTxVO[] loParameterVO = new ParameterTxVO[rowCount];

	for (int i = 0; i < rowCount; i++) {
	    ioLogger.debug("" + model.getValueAt(i, 0) + " " +
		model.getValueAt(i, 1));
	}

	int j = 1;

	for (int i = 0; i < rowCount; i++) {
	    loParameterVO[i] = new ParameterTxVO();

	    Date lDate = null;
	    String lsParameterKey = (String) model.getValueAt(i, 0).toString()
						  .trim();
	    String lsParameterValue = " ";

	    if (model.getValueAt(i, 1) != null) {
		lsParameterValue = model.getValueAt(i, 1).toString().trim();
	    }

	    String lsFlag = model.getValueAt(i, 2).toString();
	    loParameterVO[i].setParameterKey(lsParameterKey);
	    loParameterVO[i].setParameterID(lsParameterId);
	    loParameterVO[i].setCreatedBy(loTransferObject.getUserName());
	    loParameterVO[i].setModifiedBy(loTransferObject.getUserName());
	    loParameterVO[i].setProcessDate(dtTemp);
	    loParameterVO[i].setSerialNumber(j);
	    loParameterVO[i].setConcurrencyObject(loGlobalUCSConcurrency);
	    if (lsFlag.equals("D")) {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		if ((model.getValueAt(i, 1) != null) &&
			(model.getValueAt(i, 1) != "")) {
		    lDate = (Date) model.getValueAt(i, 1);
		    loParameterVO[i].setParameterValue(df.format(lDate));
		} else {
		    loParameterVO[i].setParameterValue("");
		}

		loParameterVO[i].setDatatypeFlag("D");
		loParameterVO[i].setToolTipText(ioParameterTXVO[i].getToolTipText());
	    } else if (lsFlag.equals("NR")) {
		loParameterVO[i].setParameterValue(lsParameterValue);
		loParameterVO[i].setDatatypeFlag("NR");
		loParameterVO[i].setToolTipText(ioParameterTXVO[i].getToolTipText());
	    } else if (lsFlag.equals("N")) {
		loParameterVO[i].setParameterValue(lsParameterValue);
		loParameterVO[i].setDatatypeFlag("N");
		loParameterVO[i].setToolTipText(ioParameterTXVO[i].getToolTipText());
	    } else if (lsFlag.equals("C")) {
		loParameterVO[i].setParameterValue(lsParameterValue);
		loParameterVO[i].setDatatypeFlag("C");
		loParameterVO[i].setToolTipText(ioParameterTXVO[i].getToolTipText());
	    }

	    j++;
	}
	int lnConfirm=1;
if(checkTableDataChanged(loParameterVO))
{
	 lnConfirm = UIController.getInstance().showMessage("BAT_C_0004",
		this);
}

	if (lnConfirm == 0 ) {
	    loTransferObject.setActionID("BAT_A_APX_PARAMETER");
	    loTransferObject.setSubEventID("BAT_SE_SAVE");

	    Hashtable loInputHash = new Hashtable();
	    loInputHash.put("INPUT", loParameterVO);
	    loTransferObject.setRequestData(loInputHash);
	    UIController.getInstance().delegateActionToServer(loTransferObject,
		this, true);
	}
    } //btnSave ActionPerformed

    public void getData() {
	TransferObject loTransferObject = new TransferObject();
	Hashtable loInputHash = new Hashtable();
	loTransferObject.setNextScreenId("BAT001");
	loTransferObject.setRequestData(loInputHash);
	loTransferObject.setActionID("BAT_A_APX_PARAMETER");
	loTransferObject.setSubEventID("BAT_SE_FETCH_PROGRAM");
	UIController.getInstance().delegateActionToServer(loTransferObject,
	    this, true);
    }

    void setClearHandler() {
	clearHandler.addObjects(pnlZAnlegen);
	clearHandler.addObjects(pnlButton);
    }

    public void setData(TransferObject poTransObj) {
	ioLogger.info("setData()");
	ibFlag = false;

	String lsSubEventId = poTransObj.getSubEventID();

	if ("BAT_SE_FETCH_PROGRAM".equalsIgnoreCase(lsSubEventId)) {
	    Hashtable loresponse = poTransObj.getResponseData();
	    if(loresponse.get("null")!= null && loresponse.get("null").equals("null")){
				UIController.getInstance().showMessage("BAT_E_0002", this);
				disableAllFields();
			}else{
	    loFunctionListVO = (HashMap) loresponse.get("FunctionListVO");

	    String lsProcessDt = (String) loresponse.get("ProcessDate");

	    try {
		dtProcessDateFromdb = UCSUtil.stringToDate(lsProcessDt);
		ioLogger.debug("Date" + dtProcessDateFromdb);
		dtProcessDate.setDate(dtProcessDateFromdb);
	    } catch (ParseException ex) {
		ex.printStackTrace();
	    }

	    ioLogger.debug("Process Date Returned is: " + lsProcessDt);

	    String[] loFunctionArray = new String[loFunctionListVO.size() + 1];

	    loFunctionArray[0] = "                            ";
	    Set loFuncKey = loFunctionListVO.keySet();
	    Iterator loKeyIterator = loFuncKey.iterator();
	    int lnFuncCount = 1;

	    while (loKeyIterator.hasNext()) {
		loFunctionArray[lnFuncCount] = (String) loKeyIterator.next();
		lnFuncCount++;
	    }

	    cmbCategory.setItems(loFunctionArray);
		}
	} else if ("BAT_SE_FETCH".equalsIgnoreCase(lsSubEventId)) {
	    Hashtable loResponse = poTransObj.getResponseData();

	    ParameterTxVO[] loParameterTXVO = null;

	    if (loResponse.get("PARAMETERTXVO").toString().equals("")) {
		UIController.getInstance().showMessage("BAT_E_0002", this);
		btnModify.setEnabled(false);
		btnSaveChanges.setEnabled(false);
		btnClear.setEnabled(false);
		btnRestDflt.setEnabled(false);
		model.setData(null);
		statusBar.setText("    ");
		txtToolTip.setText("");
	    } else {
		loParameterTXVO = (ParameterTxVO[]) loResponse.get(
			"PARAMETERTXVO");
		ioParameterTXVO = loParameterTXVO;
		loGlobalUCSConcurrency=loParameterTXVO[0].getConcurrencyObject();
		Vector loData = new Vector();

		int lnNumOfRecords = loParameterTXVO.length;

		for (int i = 0; i < lnNumOfRecords; i++) {
		    String lsParameterKey = loParameterTXVO[i].getParameterKey();
		    String lsParameterValue = loParameterTXVO[i].getParameterValue();
		    String lsDatatypeFlag = loParameterTXVO[i].getDatatypeFlag();

		    Vector eachRow = new Vector();
		    Date temp = null;

		    if (loParameterTXVO[i].getDatatypeFlag().equals("D")) {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

			try {
			    if ((lsParameterValue != null) &&
				    !lsParameterValue.equals("") &&
				    !lsParameterValue.equals("null")) {
				temp = (Date) df.parse(lsParameterValue);
			    }
			} catch (ParseException ex1) {
			    ex1.printStackTrace();
			}

			eachRow.add(lsParameterKey);
			eachRow.add(temp);
			eachRow.add(lsDatatypeFlag);
			loData.add(eachRow);
		    } else if (loParameterTXVO[i].getDatatypeFlag().equals("NR")) {
			ioLogger.debug("loParameterTXVO[i].getParameterValue()" +
			    loParameterTXVO[i].getParameterValue());
			eachRow.add(lsParameterKey);
			eachRow.add(lsParameterValue);
			eachRow.add(lsDatatypeFlag);
			loData.add(eachRow);
		    } else {
			eachRow.add(lsParameterKey);
			eachRow.add(lsParameterValue);
			eachRow.add(lsDatatypeFlag);
			loData.add(eachRow);
		    }
		}

		lnCount = 1;
		lnCountInt=1;

		for (int i = 0; i < lnNumOfRecords; i++) {
		    Vector loRow = (Vector) loData.get(i);

		    if (loRow.elementAt(2).toString().equals("NR")) {
			lnCount++;
		    }
		    if (loRow.elementAt(2).toString().equals("N")) {
			lnCountInt++;
		    }
		}

		ioRangeArray = new HexNumRange[lnCount++]; // initialising nr array
		ioIntArray= new HexInteger[lnCountInt++];//initializing n array

		int num = 0;
		int num1=0;

		for (int i = 0; i < lnNumOfRecords; i++) {
		    Vector loRow = (Vector) loData.get(i);

		    if (loRow.elementAt(2).toString().equals("NR")) {
			num++;
			loRow.add(3, String.valueOf(num));
			ioRangeArray[num] = new HexNumRange(100,
				"MasterMaintenance", this);
			ioRangeArray[num].setText(loRow.elementAt(1).toString());
			ioRangeArray[num].setEditable(true);
			ioRangeArray[num].setEnabled(true);
		    }
		    if (loRow.elementAt(2).toString().equals("N")) {
			num1++;
			loRow.add(3, String.valueOf(num1));
			ioIntArray[num1] = new HexInteger(
				"MasterMaintenance", this);
			ioIntArray[num1].setText(loRow.elementAt(1).toString());
			ioIntArray[num1].setEditable(true);
			ioIntArray[num1].setEnabled(true);
		    }

		    ioLogger.debug("Row:[" + i + "]:" + loRow);
		}

		lotableView.setColumnBackground(1, Color.lightGray);
		model.setData(loData);

		for (int i = 0; i < lnNumOfRecords; i++) {
		    ioLogger.debug("Row: " + (Vector) loData.get(i));
		}

		btnModify.requestFocus();
	    }
	} else if ("BAT_SE_RESTORE_DEFAULT".equalsIgnoreCase(lsSubEventId)) {
	    ioLogger.debug("Inside Rest -->");

	    Hashtable loResponse = poTransObj.getResponseData();
	    ParameterTxVO[] loParameterTXVO = (ParameterTxVO[]) loResponse.get(
		    "PARAMETERTXVO");
	    ioParameterTXVO = loParameterTXVO;

	    Vector loData = new Vector();
	    int lnNumOfRecords = loParameterTXVO.length;

	    for (int i = 0; i < lnNumOfRecords; i++) {
		String lsParameterKey = loParameterTXVO[i].getParameterKey();
		String lsParameterValue = loParameterTXVO[i].getParameterValue();
		String lsDatatypeFlag = loParameterTXVO[i].getDatatypeFlag();
		Vector eachRow = new Vector();
		eachRow.add(lsParameterKey);
		eachRow.add(lsParameterValue);
		eachRow.add(lsDatatypeFlag);
		loData.add(eachRow);
	    }

	    model.setData(loData);
	    ibFlag = false;
	    btnModify.setEnabled(false);
	    btnSaveChanges.setEnabled(false);
	} else if ("BAT_SE_SAVE".equalsIgnoreCase(lsSubEventId)) {
	    Hashtable loResponse = poTransObj.getResponseData();
	    ibFlag = false;

	    if (loResponse.get("save").equals("true")) {
		UIController.getInstance().showMessage("PAR_I_0082", this);
		lotableView.getTable().clearSelection();
		lotableView.repaint();
	    }
	}
    } //setdata

    public void tableSettings() {
	ioLogger.debug("tableSettings()");

	DynamicTableCellRenderer cellRenderer = new DynamicTableCellRenderer();
	lotableView = new HexTableView("", this, cellRenderer);
	cellRenderer.dateRenderer = (pv.jfcx.PVTableRenderer) lotableView.getTable()
									 .getDefaultRenderer(java.util.Date.class);
	loColumnNames = new Vector();
	loColumnNames.addElement("PARAM NAME");
	loColumnNames.addElement("PARAM VALUE");
	loData = new Vector();
	model = new PVTableModel(loData, loColumnNames);
	cellRenderer.setModel(model);
	ParamName.setEditable(false);
	ParamName.setEnabled(false);
//	ParamName.disable();
	ParamName.setFocusTraversable(false);
	lotableView.setColumnType(0, pv.jfcx.JPVTable.TEXT, ParamName);
	ParamValue.setFocusTraversable(false);
	lotableView.setColumnType(1, pv.jfcx.JPVTable.TEXT, ParamValue);
	lotableView.setSortingCaseOn(false);
	lotableView.getTable().setShowSortIcons(false);
	lotableView.setSortColumns(0, 0, 0);
	lotableView.setReorderingAllowed(true);

	ParamValue.setEditable(false);
	lotableView.getColumn(0).setPreferredWidth((int) (lotableView.getColumn(
		0).getPreferredWidth() * 2.3));
	lotableView.getColumn(1).setPreferredWidth((int) (lotableView.getColumn(
		1).getPreferredWidth() * 2.3));

	lnTableWidth = (int) ((lotableView.getColumn(0).getPreferredWidth() * 1.0) +
	    (lotableView.getColumn(1).getPreferredWidth() * 1.0)) + (int) 2.95;
	lnTableHeight = 11 * 18;
	lotableView.setEnableSortData(false);
	lotableView.setPreferredSize(new Dimension(lnTableWidth, lnTableHeight));
	lotableView.setMinimumSize(lotableView.getPreferredSize());
	lotableView.getTable().getTableHeader().setResizingAllowed(true);
	lotableView.getTable().getTableHeader().setReorderingAllowed(false);
	lotableView.getTable().setRowSelectionAllowed(false);
	lotableView.setAutoscrolls(false);
	lotableView.setSortingNulls(false);
	lotableView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	lotableView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	lotableView.setTableFont(HexFont.getNormalFont());
	lotableView.setColumnBackground(0, Color.lightGray);
	lotableView.setColumnBackground(1, Color.lightGray);
	lotableView.getTable().setCellSelectionEnabled(false);
	lotableView.setFont(HexFont.getNormalFont());
	lotableView.setForeground(Color.black);
    } //tableSettings
	public void disableAllFields() {
	    ioLogger.debug("disableAllFields()");
	    dtProcessDate.setEnabled(false);
	    lblCategory.setEnabled(false);
	    lblModuleID.setEnabled(false);
	    lblProcessDate.setEnabled(false);
	    lblProgramName.setEnabled(false);
	    lblStepID.setEnabled(false);
	    cmbCategory.setEnabled(false);
	    cmbProgramName.setEnabled(false);
	    cmbStepID.setEnabled(false);
	    txtModuleID.setEnabled(false);
	    lotableView.setEnabled(false);
	    btnClear.setEnabled(false);
	    btnFetch.setEnabled(false);
	    btnRestDflt.setEnabled(false);
	    btnSaveChanges.setEnabled(false);
	    btnModify.setEnabled(false);

	}
    class DynamicTableCellRenderer extends JPVTable {
	public pv.jfcx.PVTableRenderer dateRenderer = null;
	public pv.jfcx.PVTableRenderer intRenderer = new pv.jfcx.PVTableRenderer(JPVTable.INTEGER);
	public pv.jfcx.PVTableRenderer textRenderer = new pv.jfcx.PVTableRenderer(JPVTable.TEXT);
	public pv.jfcx.PVTableRenderer rangeRenderer = new pv.jfcx.PVTableRenderer(JPVTable.NUMERIC_TEXT);
	JPVDatePlus period = new JPVDatePlus();
	javax.swing.table.TableCellEditor dateEditor = new pv.jfcx.PVTableEditor(period,
		pv.jfcx.JPVTable.DATE_LONG_PLUS);
	public pv.jfcx.PVTableEditor intEditor = new pv.jfcx.PVTableEditor(pv.jfcx.JPVTable.INTEGER);
	public pv.jfcx.PVTableEditor textEditor = new pv.jfcx.PVTableEditor(pv.jfcx.JPVTable.TEXT);
	public pv.jfcx.PVTableEditor rangeEditor = new pv.jfcx.PVTableEditor(pv.jfcx.JPVTable.TEXT);

	public TableCellRenderer getCellRenderer(int row, int col) {
	    dateRenderer.setType(JPVTable.DATE_PLUS);
	    dateRenderer.setFormat(new java.text.SimpleDateFormat("dd.MM.yyyy"));
	    dateRenderer.setHorizontalAlignment(SwingConstants.LEFT);
	    intRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	    rangeRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

//	    int editingrow = lotableView.getTable().getEditingRow();
//	    String datatype = (String) model.getValueAt(editingrow, 2);

	    if (ioParameterTXVO[row].getDatatypeFlag().equalsIgnoreCase("D")) {
		dateRenderer.setFont(HexFont.getNormalFont());

		if (col == 1) {
		    dateRenderer.setBackground(Color.lightGray);

		    if (ibFlag) {
			dateRenderer.setBackground(Color.white);
		    }

		    dateRenderer.setToolTipText("" +
			ioParameterTXVO[row].getToolTipText());

		    return dateRenderer;
		}
	    }

	    if (ioParameterTXVO[row].getDatatypeFlag().equalsIgnoreCase("N")) {
		if (col == 1) {
		    intRenderer.setBackground(Color.lightGray);
		    intRenderer.setFont(HexFont.getNormalFont());

		    if (ibFlag) {
			intRenderer.setBackground(Color.white);
			intRenderer.setToolTipText("" +
			    ioParameterTXVO[row].getToolTipText());
		    }

		    intRenderer.setToolTipText("" +
			ioParameterTXVO[row].getToolTipText());

		    return intRenderer;
		}
	    }

	    if (ioParameterTXVO[row].getDatatypeFlag().equalsIgnoreCase("NR")) {
		if (col == 1) {
//		    int i = lotableView.getTable().getEditingRow();
		    rangeRenderer.setBackground(Color.lightGray);

		    if (ibFlag) {
			rangeRenderer.setBackground(Color.white);
		    }

		    rangeRenderer.setToolTipText("" +
			ioParameterTXVO[row].getToolTipText());

		    return rangeRenderer;
		}
	    }

	    if (ioParameterTXVO[row].getDatatypeFlag().equalsIgnoreCase("C")) {
		if (col == 1) {
		    textRenderer.setBackground(Color.lightGray);
		    textRenderer.setFont(HexFont.getNormalFont());

		    if (ibFlag) {
			textRenderer.setBackground(Color.white);
		    }

		    textRenderer.setToolTipText("" +
			ioParameterTXVO[row].getToolTipText());

		    return textRenderer;
		}
	    }

	    return super.getCellRenderer(row, col);
	}

	public TableCellEditor getCellEditor(int row, int col) {
	    ioLogger.info("getCellEditor()");
	    period.setShowCentury(true);
	    period.setFormat(JPVDate.DMY);
	    period.setForeground(Color.black);
	    period.setFont(HexFont.getNormalFont());
	    period.setFreeEntry(false);

	    int editingrow = lotableView.getTable().getEditingRow();
	    String datatype = (String) model.getValueAt(editingrow, 2);

	    if (datatype.equals("D") && ibFlag) {
		if (col == 1) {
		    statusBar.setText(resources.getString("TypeofData")+": Date");
		    //lblToolTipText.setText(ioParameterTXVO[row].getToolTipText());
		     txtToolTip.setText(ioParameterTXVO[row].getToolTipText());
		    return dateEditor;
		}
	    }

	    if (datatype.equals("N") && ibFlag) {
//                if (col == 1) {
//                    statusBar.setText(resources.getString("TypeofData")+": Numeric");
//                    lblToolTipText.setText(ioParameterTXVO[row].getToolTipText());
//                   testint.addFocusListener(new FocusListener() {
//                       public void focusLost(FocusEvent e) {
//                           int i = lotableView.getTable().getEditingRow();
//                           lotableView.setValueAt(new String(
//                                   testint.getText()), i, 1);
//                           }
//                       public void focusGained(FocusEvent e) {
//                           int i = lotableView.getTable().getEditingRow();
//                           lotableView.setValueAt(new String(
//                                   testint.getText()), i, 1);
//                       }
//                   });
//                    intEditor.m_comp=testint;
		    //return intEditor;
		//}
		    if (col == 1) {
			int num = Integer.parseInt(model.getValueAt(editingrow, 3)
							.toString());
			int i = lotableView.getTable().getEditingRow();
			ioIntArray[num].setHorizontalAlignment(SwingConstants.LEFT);
			ioIntArray[num].addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
				    int i = lotableView.getTable().getEditingRow();
				    int num = Integer.parseInt(model.getValueAt(i, 3)
								    .toString());
				    lotableView.setValueAt(new String(
						    ioIntArray[num].getText()), i, 1);
				}

				public void focusGained(FocusEvent e) {
				    int i = lotableView.getTable().getEditingRow();
				    int num = Integer.parseInt(model.getValueAt(i, 3)
								    .toString());
				    lotableView.setValueAt(new String(
						    ioIntArray[num].getText()), i, 1);
				}
			    });

			/*ioIntArray[num].setNextFocusableComponent(lotableView.getComponentAt(
				i, 1));*/
			lotableView.setValueAt(new String(
					ioIntArray[num].getText()), i, 1);
			intEditor.m_comp = ioIntArray[num];
			statusBar.setText(resources.getString("TypeofData")+": Numeric");
//                        lblToolTipText.setText(ioParameterTXVO[row].getToolTipText());
			txtToolTip.setText(ioParameterTXVO[row].getToolTipText());
			return intEditor;
		    }

	    }
//                    else {
//                if ((col == 1) && (model.getValueAt(editingrow, 3) != null)) {
//                    int num = Integer.parseInt(model.getValueAt(editingrow, 3)
//                                                    .toString());
//                    int i = lotableView.getTable().getEditingRow();
//                    ioIntArray[num].setEditable(false);
//                }
//            }

	    if (datatype.equals("NR") && ibFlag) {
		if (col == 1) {
		    int num = Integer.parseInt(model.getValueAt(editingrow, 3)
						    .toString());
		    int i = lotableView.getTable().getEditingRow();
		    ioRangeArray[num].setHorizontalAlignment(SwingConstants.RIGHT);
		    ioRangeArray[num].addFocusListener(new FocusListener() {
			    public void focusLost(FocusEvent e) {
				int i = lotableView.getTable().getEditingRow();
				int num = Integer.parseInt(model.getValueAt(i, 3)
								.toString());
				lotableView.setValueAt(new String(
					ioRangeArray[num].getText()), i, 1);
			    }

			    public void focusGained(FocusEvent e) {
				int i = lotableView.getTable().getEditingRow();
				int num = Integer.parseInt(model.getValueAt(i, 3)
								.toString());
				lotableView.setValueAt(new String(
					ioRangeArray[num].getText()), i, 1);
			    }
			});

		   /* ioRangeArray[num].setNextFocusableComponent(lotableView.getComponentAt(
			    i, 1));*/
		    lotableView.setValueAt(new String(
			    ioRangeArray[num].getText()), i, 1);
		    rangeEditor.m_comp = ioRangeArray[num];
		    statusBar.setText(resources.getString("TypeofData")+": Numeric Range");
//                    lblToolTipText.setText(ioParameterTXVO[row].getToolTipText());
		    txtToolTip.setText(ioParameterTXVO[row].getToolTipText());
		    return rangeEditor;
		}
	    } else {
		if ((col == 1) && (model.getValueAt(editingrow, 3) != null)) {
		    int num = Integer.parseInt(model.getValueAt(editingrow, 3)
						    .toString());
//		    int i = lotableView.getTable().getEditingRow();
		    ioRangeArray[num].setEditable(false);
		}
	    }

	    if (datatype.equals("C") && ibFlag) {
		if (col == 1) {
		    statusBar.setText(resources.getString("TypeofData")+": Character");
		   // lblToolTipText.setText(ioParameterTXVO[row].getToolTipText());
		   txtToolTip.setText(ioParameterTXVO[row].getToolTipText());
		    return textEditor;
		}
	    }

	    return super.getCellEditor(row, col);
	} //getCellEditor
    } //class DynamicTableCellRenderer
//Method to sort a string array added for UAT defect 13741 on 31st Jan 2006- Venkat
    public String[] sortArray(String[] psInput){
        String lsOutput[]=new String[psInput.length];
        Set loStrSet=new TreeSet(String.CASE_INSENSITIVE_ORDER);
        for(int i=0 ;i<psInput.length;i++){    	
            loStrSet.add(psInput[i].toUpperCase());
        }
        loStrSet.toArray(lsOutput);
        return lsOutput;
    }
} //class
