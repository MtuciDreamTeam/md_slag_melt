/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdslagmelt.initialdata;

import java.io.Serializable;

/**
 *
 * @author dmitry
 */
public class InitialData implements Serializable {
    //Cm_0 и до него
    public double MSD_x, MSD_y, MSD_z, J_x, J_y, J_z, MSD_n, MSD, mas,mas_SI,mas_vn, Mean_massiv; 
    public int Nrec_md_dat, Nst0,Nst2, N_phase, Nst_sm, Nsr, NSR, dl_rec_fr,lshap_fr, lshap_kr,
    ls,lt,x_krd,y_krd,z_krd,r_krd,dl_sv_krd, N_tip_sv, k_vo_sv;
    String tip_mol, tip_in, n_pr_in,po,sig_n, Name_fi, ch_ir1, ch_ir2, get_dat, dir_cur, txt_cur,
    name_dir, name_system, name_mol_dol, slash,pust,ch1, txt1_ptl,txt2_ptl; 
    /* Названия файлов для main.exe 
    character*50 dir_cur, txt_cur, nm_1, nm_2, nm_3, nm_4, nm_5,
    * nm_6, nm_7, nm_8, nm_9, nm_10,nm_11,nm_12,nm_13,nm_14,nm_15,
    * nm_16,nm_17,nm_18,nm_19,nm_20,nm_21,nm_22,nm_23,nm_24,nm_25
    */
    int Tab_1,Tab_2,Tab_3,Tab_3_1,Tab_4,Tab_4_1,Tab_5,Tab_5_1, Tab_6,Tab_7,Tab_8,Tab_9,Tab_10,
    Tab_11, Tab_12,Tab_13,Tab_14,Tab_15,Tab_16;
    //Common/Cm_1/   
    int G[] = new int[3]; 
    int q_tip[] = new int[3];
    int ROGRR[][][][] = new int[3][3][1][6];        
    
}
