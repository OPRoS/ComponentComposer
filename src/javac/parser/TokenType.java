package javac.parser;
import java.io.StreamTokenizer;

public interface TokenType
{
	public static final int 
		TT_EOF = StreamTokenizer.TT_EOF, 
		TT_EOL = StreamTokenizer.TT_EOL, 
		TT_NUMBER = StreamTokenizer.TT_NUMBER, 
		TT_WORD = StreamTokenizer.TT_WORD;

	public static final int
		_str_token = 100, _mark_token = 200, _stmt_token = 300, _emit = 400;
	
	public static final int 
		e_nothing			= -1,
		e_push				= _emit + 1,
		
		e_class_begin		= _emit + 10,
		e_interface_begin	= _emit + 11,
		e_class_id			= _emit + 12,
		e_class_end			= _emit + 13,
		
		e_package			= _emit + 21,
		e_import			= _emit + 31,
		e_extends			= _emit + 41,
		e_implements		= _emit + 51,

		e_field				= _emit + 60,
		e_more_field		= _emit + 61,
		e_array				= _emit + 62,
		e_name				= _emit + 63,
		e_array_after_field	= _emit + 65,

		e_method			= _emit + 71,
		e_parameter			= _emit + 72,
		e_parameter_name	= _emit + 73,
		e_throws			= _emit + 74,
		e_array_after_method= _emit + 75,
		
		e_constructor		= _emit + 81,
		e_static_block		= _emit + 82,

		e_block				= _emit + 91,
		e_expr				= _emit + 92,

		e_option			= _emit + 1000;

	public static final int
		t_id = 1,
		t_eof = 2,
		t_error = 3,
		
		t_package 		= _str_token + 0, 
		t_import 		= _str_token + 1, 
		t_class 		= _str_token + 2, 
		t_interface 	= _str_token + 3,
		t_extends 		= _str_token + 4, 
		t_implements 	= _str_token + 5,
		t_public 		= _str_token + 6, 
		t_protected 	= _str_token + 7, 
		t_private 		= _str_token + 8,
		t_abstract 		= _str_token + 9, 
		t_strictfp 		= _str_token + 10,
		t_static 		= _str_token + 11, 
		t_final 		= _str_token + 12, 
		t_volatile 		= _str_token + 13, 
		t_transient 	= _str_token + 14,
		t_synchronized 	= _str_token + 15, 
		t_native 		= _str_token + 16, 
		t_throws 		= _str_token + 17,
		
		t_semicolon 	= _mark_token + 0, 
		t_dot 			= _mark_token + 1, 
		t_comma 		= _mark_token + 2, 
		t_assign 		= _mark_token + 3, 
		t_star 			= _mark_token + 4,
		t_l_begin 		= _mark_token + 5, 
		t_l_end 		= _mark_token + 6, // l: [] m: {} s: ()
		t_m_begin 		= _mark_token + 7, 
		t_m_end 		= _mark_token + 8, 
		t_s_begin 		= _mark_token + 9, 
		t_s_end 		= _mark_token + 10,

		t_block			= _stmt_token + 0,
		t_expr			= _stmt_token + 1;

	public static final String string_tokens[] = {
		"package", "import", "class", "interface", "extends",
		"implements", "public", "protected", "private", "abstract",
		"strictfp", "static", "final", "volatile", "transient", "synchronized",
		"native", "throws"
	};

	public static final char mark_tokens[] = {
		';', '.', ',', '=', '*', '[', ']', '{', '}', '(', ')'
	};
}
