package com.example.calc112;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener{

	boolean swb=false,swd=true;
	int BaseA,BaseB;
	double numero=0,numeroB=0,resultado=0;
	String pEntera="0",pDecimal="";
	double deciaml=0;
	String cadenaNum="0";
	Spinner spBaseuno,spBasedos;
	EditText campotxt;
	ArrayAdapter<String> aaBaseuno,aaBasedos,aaLimpiar;
	String [] Base=new String[]{"(2)Binario","(3)Ternario","(4)Cuaternarios","(5)Quinario","(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        campotxt=(EditText)findViewById(R.id.etxtContenido);
        campotxt.setText(cadenaNum);
        
        spBaseuno=(Spinner)findViewById(R.id.baseUno);
        spBasedos=(Spinner)findViewById(R.id.baseDos);
        
        //Escucha de accion
        spBaseuno.setOnItemSelectedListener(this);
        spBasedos.setOnItemSelectedListener(this);
        
        //Agregamos el array
        aaBaseuno=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Base);
        aaBasedos=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Base);
        
        //Asignamos al spinner del layout de la aplicacion
        spBaseuno.setAdapter(aaBaseuno);
        spBasedos.setAdapter(aaBasedos);
    }
    
    public void Convertir(View v)
    {
		double mDecimal=0;
		long mEntero=0;
    	swb=true;
    	if(BaseB==8)
    	{
			double res=0;
			if (pEntera.compareTo("0")>0) {
				mEntero = this.BaseXBase102(pEntera);
				campotxt.setText(mEntero + "");
			}
			if (pDecimal.compareTo("0")>0)
			{
				mDecimal=this.DecimalBaseXBase10(pDecimal);
				campotxt.setText((mEntero+mDecimal)+"");
			}
    	}
    	if(BaseA==8)
    	{
			if (pEntera.compareTo("0")>0)
				campotxt.setText(this.Base10BaseX(Integer.parseInt(pEntera)));
    		if (pDecimal.compareTo("0")>0)
			{
				campotxt.setText(this.Base10BaseX(Long.parseLong(pEntera))+"."+(this.DecimalBase10BaseX(pDecimal)));
			}
    	}
    	if(BaseA!=8&&BaseB!=8)
    	{
			if (pEntera.compareTo("0")>0)
    			campotxt.setText(this.BaseXBaseY(pEntera));
			if (pDecimal.compareTo("0")>0) {
				campotxt.setText(this.BaseXBaseY(pEntera)+"."+(this.DecimalBaseXBaseY(pDecimal)));

			}
    	}
    }

	//Convercion de decimales Base X a Base Y
	public String DecimalBaseXBaseY(String x)
	{
		String res=this.DecimalBase10BaseX(this.ConvertirDecimalEntero(this.DecimalBaseXBase10(x)));
		return res;
	}
	public String ConvertirDecimalEntero(double x){
		String y=x+"",cad="";
		char w;
		for (int i=1;i<y.length();i++)
		{
			if (y.charAt(i)!='.')
				cad=cad+y.charAt(i);
		}
		return cad;
	}
	//Convercion de decimales de base X a base 10
	public double DecimalBaseXBase10(String x)
	{
		int  aux;
		double res=0;
		for (int i = 1; i <= x.length(); i++) {
			aux=convertirCadenaANumero(x.substring(i-1,i));
			res=res+(aux*(Math.pow(BaseA+2, (i*-1))));
		}
		return res;
	}

	//Convercion de decimales de base 10 a Base X;
	public String DecimalBase10BaseX(String deci)
	{
		int cantidadDecimales=6;
		int y;
		String cadena="";
		double x=Double.parseDouble(deci)*Math.pow(10, -deci.length());
		for (int i = 1; i <= cantidadDecimales; i++) {
			y=(int)x*(BaseB+2);
			x=x*(BaseB+2)-y;
			String nu=ConvertirNumeroaCadena((int)x);
			cadena=cadena+(nu);
		}
		return cadena;
	}
    public String Base10BaseX(long x)
    {
    	int residuo;
    	long aux=x;
    	String cadena="";
    	while(aux!=0)
    	{
            residuo=(int)(((int)((int)aux/(BaseB+2))*(BaseB+2))-aux)*-1;
            aux=(int)aux/(BaseB+2);
            if(BaseB+2>10&&residuo>9)
            {
            	cadena=this.BaseMayor10(residuo)+cadena;
            }
            else{
            	cadena=(int)residuo+cadena;
            }
    	}
    	return cadena;
    }
    public String BaseXBase10(long x)
    {
    	long r=0;
    	double aux=x;
    	int e=(int)Math.log10(x);
    	for(int i=0;i<=e;i++)
    	{
    		int n=(int)aux%10;
    		aux=(int)aux/10;
    		r=r+n*((int)Math.pow(BaseA+2, i));
    	}
    	String cadena=r+"";
    	return cadena;
    }
    public long BaseXBase102(String x)
    {
    	int a;
    	long k=0;
        int ex=x.length()-1;
        for (int i = 0; i < x.length(); i++) {
            a=convertirCadenaANumero(x.substring(i, i+1));
            k=k+a*((int)Math.pow(BaseA+2, ex));
            ex--;
        }
        return k;
    }
    public int convertirCadenaANumero(String n)
    {
    	int nn=0;
    	if(n.equals("A"))
    		nn=10;
    	if(n.equals("B"))
    		nn=11;
    	if(n.equals("C"))
    		nn=12;
    	if(n.equals("D"))
    		nn=13;
    	if(n.equals("E"))
    		nn=14;
    	if(n.equals("F"))
    		nn=15;
    	if (!n.equals("A")&&!n.equals("B")&&!n.equals("C")&&!n.equals("C")&&!n.equals("D")&&!n.equals("E")&&!n.equals("F")) {
            nn=Integer.parseInt(n);
        }
    	return nn;
    }
	public static String ConvertirNumeroaCadena(int x)
	{
		String cad="";
		switch(x)
		{
			case 10:    cad="A";break;
			case 11:    cad="B";break;
			case 12:    cad="C";break;
			case 13:    cad="D";break;
			case 14:    cad="E";break;
			case 15:    cad="F";break;
			default: cad=x+"";
		}
		return cad;
	}
    public Boolean VereficarNumero(String N)
    {
    	char a;
    	for(int i=0;i<N.length();i++)
    	{
    		a=N.charAt(i);
    		if(a=='A')
    			return false;
    		if(a=='B')
    			return false;
    		if(a=='C')
    			return false;
    		if(a=='D')
    			return false;
    		if(a=='E')
    			return false;
    		if(a=='F')
    			return false;
    	}
    	return true;
    }
    public String BaseXBaseY(String x)
    {
    	String r=this.Base10BaseX(this.BaseXBase102(x));
    	return r;
    }
    public String BaseMayor10(int n)
    {
    	String r="";
    	switch(n)
    	{
    	case 10: r="A";
    	break;
    	case 11: r="B";
    	break;
    	case 12: r="C";
    	break;
    	case 13: r="D";
    	break;
    	case 14: r="E";
    	break;
    	case 15: r="F";
    	break;
    	}
    	return r;
    }
    

   //Botones de Numeros
    public void CambioSpinner(String b)
    {
    	//String cad=this.DigitoMayor(campotxt.getText().toString());
    	//if(b.compareTo(cad)>=0)
    	//{
    	//	if(b.equals("0")||b.equals("1"))
    	//    	Base=new String[]{"(2)Binario","(3)Ternario","(4)Cuaternarios","(5)Quinario","(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("2"))
    	//    	Base=new String[]{"(3)Ternario","(4)Cuaternarios","(5)Quinario","(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("3"))
    	//    	Base=new String[]{"(4)Cuaternarios","(5)Quinario","(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("4"))
    	//    	Base=new String[]{"(5)Quinario","(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("5"))
    	//    	Base=new String[]{"(6)Senario","(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("6"))
    	//    	Base=new String[]{"(7)Septenario","(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("7"))
    	//    	Base=new String[]{"(8)Octal","(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("8"))
    	//    	Base=new String[]{"(9)Nonario","(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//    if(b.equals("9"))
    	//    	Base=new String[]{"(10)Decimal","(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
    	//	if(b.equals("A"))
        //		Base=new String[]{"(11)Undecimal","(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
        //	if(b.equals("B"))
        //		Base=new String[]{"(12)Duodecimal","Base 13","Base 14","Base 15","(16)Hexadecimal"};
        //	if(b.equals("C"))
        //		Base=new String[]{"Base 13","Base 14","Base 15","(16)Hexadecimal"};
        //	if(b.equals("D"))
        //		Base=new String[]{"Base 14","Base 15","(16)Hexadecimal"};
        //	if(b.equals("E"))
        //		Base=new String[]{"Base 15","(16)Hexadecimal"};
        //	if(b.equals("F"))
        //		Base=new String[]{"(16)Hexadecimal"};
    	//}
//
    	//aaBaseuno=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Base);
    	//spBaseuno.setAdapter(aaBaseuno);
    }
    public String DigitoMayor(String a)
    {
    	String c=a.substring(0,1);
        String b="";
        for (int i = 0; i < a.length(); i++) {
            b=a.substring(i,i+1);
            if (b.compareTo(c)>0) {
                c=b;
            }
        }
        return c;
    }
    public void Cero(View v)
    {
    	this.CambioSpinner("0");
    	if(swb)
    	{
    		pEntera="0";
    		pDecimal="";
    		cadenaNum="0";
    		swb=false;
    	}
    	if(swd){
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"0";
    		else
    			pEntera="0";
    		cadenaNum=pEntera;
    	}else{

			if (pDecimal.length()==0)
				pDecimal="0";
			else
    			pDecimal=pDecimal+"0";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	
    	campotxt.setText(cadenaNum);
    }
    public void Uno(View v)
    {
    	this.CambioSpinner("1");
    	if(swb)
    	{
    		pEntera="0";
    		pDecimal="";
    		cadenaNum="0";
    		swb=false;
    	}
    	if(swd){
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"1";
    		else
    			pEntera="1";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="1";
			else
				pDecimal=pDecimal+"1";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Dos(View v){
    	this.CambioSpinner("2");
    	if(swb)
    	{
    		pDecimal="";
    		pEntera="0";
    		swb=false;
    	}
    	if(swd){
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"2";
    		else
    			pEntera="2";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="2";
			else
				pDecimal=pDecimal+"2";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Tres(View v){
    	this.CambioSpinner("3");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd){
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"3";
    		else
    			pEntera="3";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="3";
			else
				pDecimal=pDecimal+"3";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Cuatro(View v){
    	this.CambioSpinner("4");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"4";
    		else
    			pEntera="4";
    		cadenaNum=pEntera;
    	}else{
    		if (pDecimal.length()==0)
				pDecimal="4";
			else
				pDecimal=pDecimal+"4";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Cinco(View v){
    	this.CambioSpinner("5");
    	if(swb)
    	{
    		pEntera="0";pDecimal="0";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"5";
    		else
    			pEntera="5";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="5";
			else
				pDecimal=pDecimal+"5";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Seis(View v){
    	this.CambioSpinner("6");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"6";
    		else
    			pEntera="6";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="6";
			else
				pDecimal=pDecimal+"6";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Siete(View v){
    	this.CambioSpinner("7");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"7";
    		else
    			pEntera="7";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="7";
			else
				pDecimal=pDecimal+"7";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Ocho(View v){
    	this.CambioSpinner("8");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"8";
    		else
    			pEntera="8";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="8";
			else
				pDecimal=pDecimal+"8";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Nueve(View v){
    	this.CambioSpinner("9");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"9";
    		else
    			pEntera="9";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="9";
			else
				pDecimal=pDecimal+"9";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void A(View v){
    	this.CambioSpinner("A");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"A";
    		else
    			pEntera="A";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="A";
			else
				pDecimal=pDecimal+"A";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void B(View v){
    	this.CambioSpinner("B");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"B";
    		else
    			pEntera="B";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="B";
			else
				pDecimal=pDecimal+"B";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void C(View v){this.CambioSpinner("C");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"C";
    		else
    			pEntera="C";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="C";
			else
				pDecimal=pDecimal+"C";
    	}
    	campotxt.setText(cadenaNum);
    }
    public void D(View v){
    	this.CambioSpinner("D");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"D";
    		else
    			pEntera="D";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="D";
			else
				pDecimal=pDecimal+"D";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void E(View v){
    	this.CambioSpinner("E");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"E";
    		else
    			pEntera="E";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="E";
			else
				pDecimal=pDecimal+"E";
    		cadenaNum=pEntera+"."+pDecimal;
    	}
    	campotxt.setText(cadenaNum);
    }
    public void F(View v){
    	this.CambioSpinner("F");
    	if(swb)
    	{
    		pEntera="0";pDecimal="";
    		swb=false;
    	}
    	if(swd)
    	{
    		if(!pEntera.equals("0"))
    			pEntera=pEntera+"F";
    		else
    			pEntera="F";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==0)
				pDecimal="F";
			else
				pDecimal=pDecimal+"F";
    	}
    	campotxt.setText(cadenaNum);
    }
    public void Punto(View v)
    {
    	swd=false;
    }
    
    public void BorrarTodo(View v)
    {
    	pEntera="0";pDecimal="";
    	cadenaNum=pEntera+"";
    	swd=true;
    	campotxt.setText(cadenaNum);
    }
    public void Borrar(View v)
    {
    	if(swd)
    	{
    		pEntera=pEntera.substring(0,pEntera.length()-1);
    		if(pEntera.length()==0)
    			pEntera="0";
    		cadenaNum=pEntera;
    	}else{
			if (pDecimal.length()==1)
				pDecimal="";
			else
    			pDecimal=pDecimal.substring(0,pDecimal.length()-1);
    		if(pDecimal.length()==0)
    		{
    			cadenaNum=pEntera;
    			swd=true;
    		}else{
    			cadenaNum=pEntera+"."+pDecimal;
    		}
    	}    	
    	campotxt.setText(cadenaNum);
    	String dmay=this.DigitoMayor(cadenaNum);
    	this.CambioSpinner(dmay);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //Acciones de Spinners
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// Jalamos los datos seleccionados de los Spinner
		BaseA=spBaseuno.getSelectedItemPosition();
		BaseB=spBasedos.getSelectedItemPosition();
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
    
}
