package com.bruce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author rcy
 * @version 1.1.0
 * @className: MsgTest
 * @date 2022-05-12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsgTest {

    private int id;

    private String context;

    private Date date;

}

