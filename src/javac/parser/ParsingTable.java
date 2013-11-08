package javac.parser;

public interface ParsingTable extends TokenType
{
	public static final int
		STATE_BEGIN = 0, STATE_END = 44,
		STATE_CLASS_BODY = 15;
	
	public static final int [][][] _table = {
		// 0	
		{
			{t_package, 1, -1},
			{t_import, 4, -1}, 
			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin},
			
			{t_public, 8, e_option},
			{t_final, 8, e_option},
			{t_abstract, 8, e_option},
			{t_strictfp, 8, e_option},

			{t_eof, 44, -1}
		},
		// 1
		{
			{t_id, 2, e_push}
		},
		// 2
		{
			{t_dot, 1, e_push},
			{t_semicolon, 3, e_package}
		},
		// 3
		{
			{t_import, 4, -1}, 
			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin},
			
			{t_public, 8, e_option},
			{t_final, 8, e_option},
			{t_abstract, 8, e_option},
			{t_strictfp, 8, e_option},

			{t_eof, 44, -1}
		},
		// 4
		{
			{t_id, 5, e_push}
		},
		// 5
		{
			{t_dot, 6, e_push},
			{t_semicolon, 3, e_import}
		},
		// 6
		{
			{t_id, 5, e_push},
			{t_star, 7, e_push}
		},
		// 7
		{
			{t_semicolon, 3, e_import}
		},
		// 8
		{
			{t_public, 8, e_option},
			{t_final, 8, e_option},
			{t_abstract, 8, e_option},
			{t_strictfp, 8, e_option},
			
			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin}
		},
		// 9
		{
			{t_id, 10, e_class_id}
		},
		// 10
		{
			{t_extends, 11, -1},
			{t_implements, 13, -1},
			{t_m_begin, 15, -1}
		},
		// 11
		{
			{t_id, 12, e_push}
		},
		// 12
		{
			{t_m_begin, 15, e_extends},
			{t_implements, 13, e_extends},
			{t_dot, 11, e_push},
			{t_comma, 11, -1}
		},
		// 13
		{
			{t_id, 14, e_push}
		},
		// 14
		{
			{t_comma, 13, e_implements},
			{t_m_begin, 15, e_implements},
			{t_dot, 13, e_push}
		},
		// 15
		{
			{t_id, 20, e_push},
			{t_semicolon, 15, -1},

			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin},

			{t_static, 16, e_option},
			{t_volatile, 19, e_option},
			{t_transient, 19, e_option},
			{t_synchronized, 19, e_option},
			{t_native, 19, e_option},
			{t_public, 45, e_option},
			{t_protected, 45, e_option},
			{t_private, 45, e_option},
			{t_final, 45, e_option},
			{t_abstract, 45, e_option},
			{t_strictfp, 45, e_option},

			{t_m_end, 43, e_class_end}
		},
		// 16
		{
			{t_m_begin, 17, e_static_block},
			{t_id, 20, e_push}, 
			
			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin},
		
			{t_volatile, 19, e_option},
			{t_transient, 19, e_option},
			{t_synchronized, 19, e_option},
			{t_native, 19, e_option},
			{t_public, 45, e_option},
			{t_protected, 45, e_option},
			{t_private, 45, e_option},
			{t_final, 45, e_option},
			{t_abstract, 45, e_option},
			{t_strictfp, 45, e_option}
		},
		// 17
		{
			{t_block, 18, e_block}
		},
		// 18
		{
			{t_m_end, 15, -1}
		},
		// 19
		{
			{t_id, 20, e_push},
			
			{t_public, 19, e_option},
			{t_protected, 19, e_option},
			{t_private, 19, e_option},
			{t_final, 19, e_option},
			{t_volatile, 19, e_option},
			{t_transient, 19, e_option},
			{t_synchronized, 19, e_option},
			{t_native, 19, e_option},
			{t_abstract, 19, e_option},
			{t_strictfp, 19, e_option},
			{t_static, 19, e_option},
		},
		// 20
		{
			{t_dot, 47, e_push},
			{t_id, 22, e_name},
			{t_s_begin, 23, e_constructor},
			{t_l_begin, 21, -1}
		},
		// 21
		{
			{t_l_end, 20, e_array}
		},
		// 22
		{
			{t_semicolon, 15, e_field},
			{t_l_begin, 26, e_field},
			{t_assign, 28, e_field},
			{t_comma, 30, e_field},

			{t_s_begin, 23, e_method},
		},
		// 23
		{
			{t_id, 24, e_push},
			{t_s_end, 38, -1},
			{t_final, 46, e_option}
		},
		// 24
		{
			{t_id, 36, e_parameter_name},
			{t_dot, 46, e_push},
			{t_l_begin, 25, -1}
		},
		// 25
		{
			{t_l_end, 24, e_array}
		},
		// 26
		{
			{t_l_end, 27, e_array_after_field}
		},
		// 27
		{
			{t_l_begin, 26, -1},
			{t_comma, 30, e_field},
			{t_assign, 28, -1},
			{t_semicolon, 15, e_field}
		},
		// 28
		{
			{t_expr, 29, e_expr}
		},
		// 29
		{
			{t_semicolon, 15, e_field},
			{t_comma, 30, e_field}
		},
		// 30
		{
			{t_id, 31, e_more_field}
		},
		// 31
		{
			{t_comma, 30, -1},
			{t_assign, 34, -1},
			{t_semicolon, 15, -1},
			{t_l_begin, 32, -1}
		},
		// 32
		{
			{t_l_end, 31, e_array_after_field}
		},
		// 33
		{
			{t_l_end, 38, e_array_after_method}
		},
		// 34
		{
			{t_expr, 35, e_expr}
		},
		// 35
		{
			{t_comma, 30, -1},
			{t_semicolon, 15, -1}
		},
		// 36
		{
			{t_l_begin, 37, -1},
			{t_comma, 23, e_parameter},
			{t_s_end, 38, e_parameter},
		},
		// 37
		{
			{t_l_end, 36, e_array}
		},
		// 38
		{
			{t_l_begin, 33, -1},
			{t_semicolon, 15, -1},
			{t_m_begin, 41, -1}, 
			{t_throws, 39, -1}
		},
		// 39
		{
			{t_id, 40, e_push}
		},
		// 40
		{
			{t_semicolon, 15, e_throws},
			{t_comma, 39, e_throws},
			{t_dot, 48, e_push},
			{t_m_begin, 41, e_throws}
		},
		// 41
		{
			{t_block, 42, e_block}
		},
		// 42
		{
			{t_m_end, 15, -1}
		},
		// 43
		{
			{t_public, 8, e_option},
			{t_final, 8, e_option},
			{t_abstract, 8, e_option},
			{t_strictfp, 8, e_option},

			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin},

			{t_semicolon, 43, -1},
			{t_eof, 44, -1}
		},
		// 44
		{
		},
		// 45
		{
			{t_id, 20, e_push}, 

			{t_public, 45, e_option},
			{t_protected, 45, e_option},
			{t_private, 45, e_option},
			{t_final, 45, e_option},
			{t_abstract, 45, e_option},
			{t_strictfp, 45, e_option},
			{t_static, 45, e_option},
			{t_volatile, 19, e_option},
			{t_transient, 19, e_option},
			{t_synchronized, 19, e_option},
			{t_native, 19, e_option},

			{t_class, 9, e_class_begin},
			{t_interface, 9, e_interface_begin}
		},
		// 46
		{
			{t_id, 24, e_push}
		},
		// 47
		{
			{t_id, 20, e_push}
		},
		// 49
		{
			{t_id, 40, e_push}
		}
	};
}
