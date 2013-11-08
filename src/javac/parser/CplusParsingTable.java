package javac.parser;

public interface CplusParsingTable extends TokenTypeCplus{
	public static final int
	STATE_BEGIN = 0, STATE_END = 44,
	STATE_CLASS_BODY = 15;

	public static final int [][][] _table = {
		//0
		{
			
			{t_sap, 0, -1},
			{t_include, 0, -1},
			{t_p_end, 0, -1},
			{t_class, 1, e_class_begin},
			{t_semicolon, 0, -1},
			{t_eof, 44, -1}
		}
		,
		// 1
		{
			{t_id, 2, e_class_id}
		},
		// 2
		{
			
			{t_colon,7,-1},
			{t_m_begin, 3, -1}
		},
		// 3
		{
//			{t_public,4,-1},
//			{t_private,4,-1},
//			{t_m_end, 4, -1},
//			{t_comma, 5, -1},
//			{t_id, 6, e_type_put}
			{t_m_end, 0,  e_class_end},
			{t_id,3,e_put_id},
			{t_colon,3,e_scope},
			{t_semicolon, 3, e_end},
			{t_s_begin, 3, e_param_begin},
			{t_assign, 3, e_param_add_equal},
			
			{t_comma,3,e_param_add},//수정필요
			{t_s_end,3,e_param_add_end},
			{t_w, 13, -1},//~
			{t_bungi, 3, e_bungi_put},
			{t_star, 3,  e_point_put},
			{t_salsh, 13, e_jusunk_begin},
			{t_m_begin, 14,  -1},
			{t_eof, 44, -1}
			
			
		},
		// 4
		{
			{t_public,4,-1},
			{t_private,4,-1},
			{t_colon,5,-1},
			{t_semicolon, 5, -1},
			{t_l_begin,6,-1},
			{t_l_end,4,-1},
			{t_id, 6, e_type_put},
			{t_eof, 44, -1},
			{t_m_end, 0, e_class_end},
		},
		//5
		{
			{t_public,4,-1},
			{t_private,4,-1},
			{t_m_end, 0, e_class_end},
			{t_comma, 5, -1},
			{t_id, 6, e_type_put},
			{t_eof, 44, -1}
		}
		,
		//6
		{   
			{t_star, 6, -1},
			{t_s_begin, 10, e_operation},
			{t_id, 9, e_cName_put}
//			{t_until_key_value, 4, e_member_data_put}
		}//7
		,
		{
			{t_id, 8, -1}
		}
		,
		//8
		{
			{t_id, 2, e_Parent_put}
		}
		,
		//9
		{
			{t_semicolon, 5, e_member_data_put},
			{t_s_begin, 10, e_operation},
			{t_until_key_value, 4, e_member_data_put}
		}
		,
		//10
		{
			{t_until_s_end , 12, e_member_data_put}
			
		}
		,
		//11
		{
			{t_m_begin , 12, -1},
			{t_until_key_value, 4, e_member_data_put}
		}
		,
		//12
		{
			{t_colon,5,-1},
			{t_until_m_end , 4, -1}
			
		}
		,
		//13
		{
			{t_until_line,3,-1},
			
			
		}
		,
		//14
		{
			{t_until_m_end,3,-1},
			
			
		}
		
	};
}