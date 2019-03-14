/**
 *
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 *
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 * @author Dean Ashton, John Travers
 *
 */

package view.toolbars;

import managers.ActionManager;

import java.awt.Font;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


// import com.apple.eawt.Application;




/**
 * The menus used within HEAT
 */
public class MainMenu {
  private JMenuBar jMenuBar = new JMenuBar();

  /* Program menu items */
  private JMenu jMenuFile = new JMenu("File");
  private JMenuItem jMenuItemOpen = new JMenuItem("Open");
  private JMenuItem jMenuItemCloseFile = new JMenuItem();
  private JMenuItem jMenuItemPrint = new JMenuItem();
  private JMenuItem jMenuItemOptions = new JMenuItem("Options");
  private JMenuItem jMenuItemExit = new JMenuItem("Exit");
  
  //jMenuItemOptions.setTitle("Options");

  /* Edit menu items */
  private JMenu jMenuEdit = new JMenu();
  private JMenuItem jMenuItemCopy = new JMenuItem();
  private JMenuItem jMenuItemCut = new JMenuItem();
  private JMenuItem jMenuItemPaste = new JMenuItem();
  
  private JMenuItem jMenuItemUndo = new JMenuItem();
  private JMenuItem jMenuItemRedo = new JMenuItem();

  /* Run menu items */
  private JMenu jMenuRun = new JMenu();
  private JMenuItem jMenuItemCompile = new JMenuItem();
  private JMenuItem jMenuItemInterrupt = new JMenuItem();
  private JMenuItem jMenuItemTest = new JMenuItem();

  /* Help menu items */
  private JMenu jMenuHelp = new JMenu();
  private JMenuItem jMenuItemContents = new JMenuItem();
  private JMenuItem jMenuItemAbout = new JMenuItem();

  private ActionManager.UndoAction undoAction = ActionManager.getInstance()
                                                             .getUndoAction();
  private ActionManager.RedoAction redoAction = ActionManager.getInstance()
                                                             .getRedoAction();
  private JMenuItem jMenuItemSearch = new JMenuItem();

  /**
   * Creates a new MainMenu object.
   */
  public MainMenu() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialises the menus GUI compenetes
   *
   * @throws Exception if swing components fail to instantiate
   */
  public void jbInit() throws Exception {
    ActionManager am = ActionManager.getInstance();
    

    /* File Menu */
    int headingFontSize = 40;
    int fontSize = 30;
    String fontType = "Arial";
    jMenuFile.setFont(new Font(fontType, Font.PLAIN, headingFontSize));
    jMenuItemExit.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemOptions.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemOpen.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemCloseFile.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuFile.setText("File");
    jMenuFile.setMnemonic('f');
    jMenuItemOpen.setAction(am.getOpenFileAction());
    // jMenuItemPrint.setText("Print");
    // jMenuItemPrint.setMnemonic('p');
    jMenuItemCloseFile.setAction(am.getCloseFileAction());
    jMenuItemCloseFile.setEnabled(false);
    // jMenuItemPrint.setAction(am.getPrintFileAction());
    jMenuItemOptions.setAction(am.getShowOptionsAction());
    jMenuItemOptions.setMnemonic('o');
    jMenuItemExit.setText("Quit");
    jMenuItemExit.setMnemonic('Q');
    jMenuItemExit.setAction(am.getExitProgramAction());
    jMenuFile.add(jMenuItemOpen);
    jMenuFile.add(jMenuItemCloseFile);
    jMenuFile.addSeparator();
    // printing is too buggy; not essential, hence better exclude
    // jMenuFile.add(jMenuItemPrint);
    // jMenuFile.addSeparator();
    jMenuFile.add(jMenuItemOptions);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuItemExit);

    /* Edit Menu */
    jMenuEdit.setFont(new Font(fontType, Font.PLAIN, headingFontSize));
    jMenuItemCut.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemCopy.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemPaste.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemSearch.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemUndo.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemRedo.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuEdit.setText("Edit");
    //jMenuEdit.add(undoAction);
    jMenuItemUndo.setText("Undo");
    jMenuItemUndo.setAction(am.getUndoAction());
    jMenuItemUndo.setMnemonic('u');
    //jMenuEdit.add(redoAction);
    jMenuItemRedo.setText("Redo");
    jMenuItemRedo.setAction(am.getRedoAction());
    jMenuItemRedo.setMnemonic('u');
    jMenuEdit.setMnemonic('e');
    jMenuItemCut.setText("Cut");
    jMenuItemCut.setAction(am.getEditCutAction());
    jMenuItemCut.setMnemonic('t');
    jMenuItemCopy.setText("Copy");
    jMenuItemCopy.setAction(am.getEditCopyAction());
    jMenuItemCopy.setMnemonic('c');
    jMenuItemPaste.setText("Paste");
    jMenuItemPaste.setAction(am.getEditPasteAction());
    jMenuItemPaste.setMnemonic('p');
    jMenuItemSearch.setText("Find");
    jMenuItemSearch.setAction(am.getSearchAction());
    jMenuItemPaste.setMnemonic('f');
    jMenuEdit.add(jMenuItemUndo);
    jMenuEdit.add(jMenuItemRedo);
    jMenuEdit.add(jMenuItemCut);
    jMenuEdit.add(jMenuItemCopy);
    jMenuEdit.add(jMenuItemPaste);
    jMenuEdit.add(jMenuItemSearch);
    

    /* Run Menu */
    jMenuRun.setFont(new Font(fontType, Font.PLAIN, headingFontSize));
    jMenuItemCompile.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemInterrupt.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemTest.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuRun.setText("Run");
    jMenuRun.setMnemonic('r');
    jMenuItemCompile.setAction(am.getCompileAction());
    jMenuItemCompile.setText("Load & Compile");
    //jMenuItemCompile.setMnemonic('c');
    jMenuItemInterrupt.setAction(am.getInterruptAction());
    jMenuItemInterrupt.setText("Interrupt");
    //jMenuItemInterrupt.setMnemonic('i');
    jMenuItemTest.setAction(am.getTestAction());
    jMenuItemTest.setText("Test");
    //jMenuItemTest.setMnemonic('t');
    jMenuRun.add(jMenuItemCompile);
    jMenuRun.add(jMenuItemInterrupt);
    jMenuRun.add(jMenuItemTest);

    /* Help Menu */
    jMenuHelp.setFont(new Font(fontType, Font.PLAIN, headingFontSize));
    jMenuItemAbout.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuItemContents.setFont(new Font(fontType, Font.PLAIN, fontSize));
    jMenuHelp.setText("Help");
    jMenuHelp.setMnemonic('H');
    jMenuItemContents.setAction(am.getShowHelpAction());
    jMenuItemAbout.setText("About");
    jMenuItemAbout.setAction(am.getShowAboutAction());
    jMenuItemAbout.setMnemonic('a');
    jMenuHelp.add(jMenuItemContents);
    jMenuHelp.addSeparator();
    jMenuHelp.add(jMenuItemAbout);

    /* Main Bar */
    jMenuBar.add(jMenuFile);
    jMenuBar.add(jMenuEdit);
    jMenuBar.add(jMenuRun);
    jMenuBar.add(jMenuHelp);
    
    // Mac specific stuff
    // Application app = Application.getApplication();
    // app.setAboutHandler(null);
    // app.setPreferencesHandler(null);
    // app.setQuitHandler(null);
    
    
  }

  /**
   * Returns the {@link JMenuBar} 
   *
   * @return the JMenuBar itself
   */
  public JMenuBar getToolBar() {
    return jMenuBar;
  }

  /**
   * Updates undo and redo command states
   */
  public void updateUndoRedo() {
    undoAction.updateUndoState();
    redoAction.updateRedoState();
    ActionManager am = ActionManager.getInstance();
    am.getToolbarUndoAction().updateUndoState();
    am.getToolbarRedoAction().updateRedoState();
  }

  /**
   * Enables or disables the close file command
   *
   * @param enabled the desired state of the close command
   */
  public void setCloseEnabled(boolean enabled) {
    jMenuItemCloseFile.setEnabled(enabled);
  }
  
    
  public void setInterruptEnabled(boolean enabled) {
    jMenuItemInterrupt.setEnabled(enabled);
  }
  
  public void setTestEnabled(boolean enabled) {
    jMenuItemTest.setEnabled(enabled);
  }
  
  public void setCompileEnabled(boolean enabled) {
    jMenuItemCompile.setEnabled(enabled);
  }
  

}
